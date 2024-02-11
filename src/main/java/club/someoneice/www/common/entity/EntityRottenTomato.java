package club.someoneice.www.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRottenTomato extends EntityThrowable {
    public EntityRottenTomato(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityRottenTomato(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityRottenTomato(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    protected void onImpact(MovingObjectPosition position) {
        if (position.entityHit != null) {
            position.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2);
        }

        for (int i = 0; i < 8; ++i) {
            this.worldObj.spawnParticle("crit", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
