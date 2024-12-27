package com.gunmetalblack.mfff.common.reg;

import com.google.common.collect.ImmutableSet;
import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.entity.DisplayTextEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MFFF.MODID)
public final class EntityReg {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MFFF.MODID);

    public static final RegistryObject<EntityType<DisplayTextEntity>> DISPLAY_TEXT = registerBlastUtilityEntity("display_text_entity", DisplayTextEntity::new, 1F, 1F);
    public static <T extends Entity> RegistryObject<EntityType<T>> registerBlastUtilityEntity(String entityName, EntityType.IFactory<T> entityConstructor) {
        return registerBlastUtilityEntity(entityName, entityConstructor, 1F, 2F);
    }

    public static <T extends Entity> RegistryObject<EntityType<T>> registerBlastUtilityEntity(String entityName, EntityType.IFactory<T> entityConstructor, float width, float height) {
        //noinspection RedundantTypeArguments
        return ENTITIES.register(
                entityName,
                ()-> {
                    // We have to use the constructor directly instead of the builder since we want to look up the
                    // current render distance for trackingRangeSupplier, but the builder only accepts a constant
                    // integer, and it runs before the DedicatedServer object is instantiated.
                    Util.fetchChoiceType(TypeReferences.ENTITY_TREE, entityName);
                    return new EntityType<>(
                            entityConstructor, // factory
                            EntityClassification.MISC, // category
                            true, // serialize
                            true, // summon
                            true, // fireImmune
                            true, // canSpawnFarFromPlayer
                            ImmutableSet.of(), // immuneTo
                            EntitySize.scalable(width, height), // dimensions
                            5, // clientTrackingRange
                            3, // updateInterval
                            entityType -> false, // velocityUpdateSupplier
                            entityType -> 48,
                            entityType -> 3, // updateIntervalSupplier
                            null //customClientFactory
                    );
                }
        );
    }

}