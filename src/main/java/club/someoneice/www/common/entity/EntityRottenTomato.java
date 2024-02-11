package club.someoneice.www.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRottenTomato extends EntityThrowable {
    public EntityRottenTomato(World world, EntityLivingBase base)
    {
        super(world, base);
    }


    @Override
    protected void onImpact(MovingObjectPosition position) {
        if (position.entityHit != null)
            position.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2);

        for (int i = 0; i < 8; ++i)
            this.worldObj.spawnParticle("crit", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

        if (!this.worldObj.isRemote)
            this.setDead();
    }
}
