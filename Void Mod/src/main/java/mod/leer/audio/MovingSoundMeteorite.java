package mod.leer.audio;

import mod.leer.entities.Meteorite;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundMeteorite extends MovingSound
{
    private final Meteorite meteorite;
    private float distance = 0.0F;

    public MovingSoundMeteorite(Meteorite meteoriteIn)
    {
        super(SoundEventHolder.meteorite, SoundCategory.NEUTRAL);
        this.meteorite = meteoriteIn;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        if (this.meteorite.isDead)
        {
            this.donePlaying = true;
        }
        else
        {
            this.xPosF = (float)this.meteorite.posX;
            this.yPosF = (float)this.meteorite.posY;
            this.zPosF = (float)this.meteorite.posZ;
           float f = MathHelper.sqrt(this.meteorite.motionX * this.meteorite.motionX + this.meteorite.motionZ * this.meteorite.motionZ);

            if ((double)f >= 0.01D)
            {
                this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
                this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 0.5F) * 0.7F;
            }
            else
            {
                this.distance = 0.0F;
                this.volume = 0.0F;
            }
        }
    }
}
