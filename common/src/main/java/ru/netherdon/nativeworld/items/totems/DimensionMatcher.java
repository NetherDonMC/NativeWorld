package ru.netherdon.nativeworld.items.totems;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public final class DimensionMatcher implements Predicate<ResourceKey<Level>>
{
    public static final DimensionMatcher OVERWORLD = of(Level.OVERWORLD.location());
    public static final DimensionMatcher NETHER = of(Level.NETHER.location());
    public static final DimensionMatcher END = of(Level.END.location());

    public static final Codec<DimensionMatcher> CODEC = Codec.xor(
        Codec.STRING.fieldOf("modid").codec(),
        ResourceLocation.CODEC
    ).xmap(DimensionMatcher::new, (matcher) -> matcher.either);

    public static final StreamCodec<RegistryFriendlyByteBuf, DimensionMatcher> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.either(
            ByteBufCodecs.STRING_UTF8,
            ResourceLocation.STREAM_CODEC
        ),
        (matcher) -> matcher.either,
        DimensionMatcher::new
    );

    private final Either<String, ResourceLocation> either;

    private DimensionMatcher(Either<String, ResourceLocation> either)
    {
        this.either = either;
    }

    public boolean match(ResourceKey<Level> dimension)
    {
        return this.either.map(
            (modId) -> dimension.location().getNamespace().equals(modId),
            (dimensionId) -> dimensionId.equals(dimension.location())
        );
    }

    @Override
    public boolean test(ResourceKey<Level> levelResourceKey)
    {
        return this.match(levelResourceKey);
    }

    public static DimensionMatcher byMod(String modId)
    {
        return new DimensionMatcher(Either.left(modId));
    }

    public static DimensionMatcher of(ResourceLocation dimensionId)
    {
        return new DimensionMatcher(Either.right(dimensionId));
    }
}
