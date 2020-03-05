# RareCardsSparkle

## How to use the API

In your main mod file, inside of the `receivePostInitialize()` method that you get from implementing `PostInitializeSubscriber` you will want to check the loader for `rare-cards-sparkle`.
```java
if (Loader.isModLoaded("rare-cards-sparkle")) {
    RareCardsSparkleHandler.init(); // this is a wrapping class that prevents imports from crashing
}
```

Inside of the SparkHandler class:
```Java
package yourmod.somepackage;

import com.blanktheevil.rarecardssparkle.RareCardsSparkle;
import com.blanktheevil.rarecardssparkle.SparkleTimer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.graphics.Color;

public class RareCardsSparkleHandler {
    public static void init() {
        RareCardsSparkle.addSparkleRule(
            "modid:SomeUniqueID", // some unique string usually prepended with modid:
            "Strikes", // this text is rendered in the mod settings menu
            new Color(0.6f, 0.7f, 1f, 0f), // this is the color of your sparkles.
            ImageMaster.GLOW_SPARK_2, // this is an AtlasRegion.
            true, // this is a boolean that allows the particles to have a random velocity upon initialization.
            new SparkleTimer(0.1f, 0.15f), // this is the minimum and maximum time between spawns in seconds.
            card -> card.tags.contains(AbstractCard.CardTags.STRIKE) // this is some Predicate that defines a boolean that must be true for a card to recieve these particles
        );
    }
}

```
