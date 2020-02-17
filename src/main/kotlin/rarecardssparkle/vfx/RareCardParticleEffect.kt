package rarecardssparkle.vfx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.helpers.Hitbox
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import rarecardssparkle.extensions.*

class RareCardParticleEffect(hb: Hitbox, color: Color?) : AbstractGameEffect() {
    private var x: Float = hb.x
    private var y: Float = hb.y
    private var oX: Float
    private var oY: Float
    private var halfWidth: Float = hb.width.div(2.0f).times(1.125f)
    private var halfHeight: Float = hb.height.div(2.0f).times(1.125f)
    private var halfDuration: Float
    private var img: AtlasRegion = ImageMaster.ROOM_SHINE_2

    init {
        duration = MathUtils.random(0.9f, 1.2f)
        scale = MathUtils.random(0.4f, 0.6f).scale()
        halfDuration = duration.div(2f)
        this.color = Color(1f, MathUtils.random(0.7f, 1f), 0.4f, 0f)
        if (color != null) {
            this.color = color
        }
        oX = MathUtils.random(-halfWidth, halfWidth).scale() - img.packedWidth.div(2f) + hb.width.div(2f)
        oY = MathUtils.random(-halfHeight, halfHeight).scale() - img.packedHeight.div(2f) + hb.height.div(2f)
        renderBehind = MathUtils.randomBoolean(0.2f.plus(scale - 0.5f))
        rotation = MathUtils.random(-5f, 5f)
    }

    override fun update() {
        applyInterpolationToAlpha()
        applyTime()
        applyDoneWhenComplete(duration < 0f)
    }

    private fun applyInterpolationToAlpha() {
        if (duration > halfDuration) {
            color.a = Interpolation.pow3In.apply(0.6f, 0f, duration.minus(halfDuration).div(halfDuration))
        } else {
            color.a = Interpolation.pow3In.apply(0f, 0.6f, duration.div(halfDuration))
        }
    }

    override fun render(sb: SpriteBatch) {
        sb.color = this.color
        sb.additiveMode()
        sb.draw(
            img,
            x + oX,
            y + oY,
            img.packedWidth.div(2f),
            img.packedHeight.div(2f),
            img.packedWidth.toFloat(),
            img.packedHeight.toFloat(),
            scale,
            scale.times(MathUtils.random(0.6f, 1.4f)),
            rotation
        )
        sb.color = Color.WHITE
        sb.normalMode()
    }

    override fun dispose() {}
}