package iguanaman.hungeroverhaul.module.harvestcraft;

import java.util.Arrays;
import java.util.List;

import com.pam.harvestcraft.HarvestCraft;
import com.pam.harvestcraft.blocks.CropRegistry;
import com.pam.harvestcraft.blocks.FruitRegistry;
import com.pam.harvestcraft.blocks.growables.BlockPamFruit;
import com.pam.harvestcraft.blocks.growables.BlockPamSapling;
import com.pam.harvestcraft.item.ItemRegistry;

import iguanaman.hungeroverhaul.common.config.Config;
import iguanaman.hungeroverhaul.module.bonemeal.BonemealModule;
import iguanaman.hungeroverhaul.module.bonemeal.modification.BonemealModification;
import iguanaman.hungeroverhaul.module.food.FoodModifier;
import iguanaman.hungeroverhaul.module.growth.PlantGrowthModule;
import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import iguanaman.hungeroverhaul.module.harvestcraft.helper.PamsModsHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary.Type;
import squeek.applecore.api.food.SetValue;

public class HarvestCraftModule
{
    public static void init()
    {
        // SETUP VALUES
        if (Config.modifySetValue && Config.useHOSetValue)
        {
            // crop special cases (unsure why these are singled out, exactly; was like this in 1.6.4)
            List<Item> lowerSaturationCrops = Arrays.asList(new Item[] {
                    CropRegistry.getFood(CropRegistry.RICE),
                    CropRegistry.getFood(CropRegistry.CHILIPEPPER),
                    CropRegistry.getFood(CropRegistry.BELLPEPPER),
                    CropRegistry.getFood(CropRegistry.BLACKBERRY),
                    CropRegistry.getFood(CropRegistry.BLUEBERRY),
                    CropRegistry.getFood(CropRegistry.CACTUSFRUIT),
                    FruitRegistry.getFood(FruitRegistry.CHERRY),
                    CropRegistry.getFood(CropRegistry.CORN),
                    CropRegistry.getFood(CropRegistry.CRANBERRY),
                    CropRegistry.getFood(CropRegistry.CUCUMBER),
                    CropRegistry.getFood(CropRegistry.EGGPLANT),
                    CropRegistry.getFood(CropRegistry.GRAPE),
                    CropRegistry.getFood(CropRegistry.KIWI),
                    CropRegistry.getFood(CropRegistry.LETTUCE),
                    CropRegistry.getFood(CropRegistry.RASPBERRY),
                    CropRegistry.getFood(CropRegistry.SPICELEAF),
                    CropRegistry.getFood(CropRegistry.STRAWBERRY),
                    CropRegistry.getFood(CropRegistry.TEALEAF),
                    CropRegistry.getFood(CropRegistry.TOMATO),
                    CropRegistry.getFood(CropRegistry.ZUCCHINI),
            });

            HarvestCraft.config.cropfoodRestore = 1;

            SetValue cropSetValue = new SetValue(HarvestCraft.config.cropfoodRestore, 0.1F);
            SetValue lowerSaturationSetValue = new SetValue(HarvestCraft.config.cropfoodRestore, 0.05F);

            // crops
            for (Item crop : CropRegistry.getFoods().values())
            {
                if (crop == CropRegistry.getFood(CropRegistry.CANTALOUPE))
                {
                    FoodModifier.setModifiedSetValue(CropRegistry.getFood(CropRegistry.CANTALOUPE), new SetValue(2, 0.1F));
                }
                else if (lowerSaturationCrops.contains(crop))
                {
                    FoodModifier.setModifiedSetValue(crop, lowerSaturationSetValue);
                }
                else
                {
                    FoodModifier.setModifiedSetValue(crop, cropSetValue);
                }
            }

            // fruits
            for (BlockPamSapling sapling : FruitRegistry.getSaplings())
            {
                FoodModifier.setModifiedSetValue(sapling.getFruitItem(), lowerSaturationSetValue);
            }

            SetValue fruitJuiceSetValue = new SetValue(2, 0.05F);
            SetValue jellySetValue = new SetValue(2, 0.1F);
            SetValue yogurtSetValue = new SetValue(2, 0.1F);
            SetValue jellySandwichSetValue = new SetValue(8, 0.45F);
            SetValue smoothieSetValue = new SetValue(3, 0.1F);
            SetValue fishSetValue = new SetValue(1, 0.25F);

            // foods
            FoodModifier.setModifiedSetValue(ItemRegistry.ediblerootItem, new SetValue(1, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sunflowerseedsItem, new SetValue(1, 0.1F));

            FoodModifier.setModifiedSetValue(ItemRegistry.calamarirawItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.calamaricookedItem, new SetValue(2, 0.1F));

            FoodModifier.setModifiedSetValue(ItemRegistry.grilledasparagusItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bakedsweetpotatoItem, new SetValue(2, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.grilledeggplantItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.toastItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cheeseItem, new SetValue(1, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.icecreamItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.grilledcheeseItem, new SetValue(7, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.applesauceItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pumpkinbreadItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.caramelappleItem, new SetValue(3, 0.1F));

            FoodModifier.setModifiedSetValue(ItemRegistry.applepieItem, new SetValue(5, 0.25F));

            FoodModifier.setModifiedSetValue(ItemRegistry.teaItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.coffeeItem, new SetValue(0, 0.0F));
            FoodModifier.setModifiedSetValue(ItemRegistry.popcornItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.raisinsItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.ricecakeItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.toastedcoconutItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.roastedpumpkinseedsItem, new SetValue(1, 0.05F));

            FoodModifier.setModifiedSetValue(ItemRegistry.applejuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.melonjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.carrotjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberryjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapejuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberryjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cherryjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.papayajuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.starfruitjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.orangejuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.peachjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.limejuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.mangojuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pomegranatejuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blackberryjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberryjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.kiwijuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cranberryjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cactusfruitjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.plumjuiceItem, new SetValue(2, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pearjuiceItem, new SetValue(2, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.apricotjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.figjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapefruitjuiceItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.persimmonjuiceItem, fruitJuiceSetValue);

            FoodModifier.setModifiedSetValue(ItemRegistry.pumpkinsoupItem, new SetValue(4, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.melonsmoothieItem, smoothieSetValue);

            FoodModifier.setModifiedSetValue(ItemRegistry.carrotsoupItem, new SetValue(4, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.glazedcarrotsItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.butteredpotatoItem, new SetValue(4, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.loadedbakedpotatoItem, new SetValue(8, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mashedpotatoesItem, new SetValue(5, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.potatosaladItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.potatosoupItem, new SetValue(4, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.friesItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.grilledmushroomItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.stuffedmushroomItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chickensandwichItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chickennoodlesoupItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chickenpotpieItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.breadedporkchopItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.hotdogItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bakedhamItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.hamburgerItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cheeseburgerItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.baconcheeseburgerItem, new SetValue(9, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.potroastItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fishsandwichItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fishsticksItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fishandchipsItem, new SetValue(8, 0.45F));

            FoodModifier.setModifiedSetValue(ItemRegistry.friedeggItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.scrambledeggItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.boiledeggItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.eggsaladItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.caramelItem, new SetValue(1, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.taffyItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spidereyesoupItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.zombiejerkyItem, new SetValue(1, 0.05F));

            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatebarItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.hotchocolateItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolateicecreamItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.vegetablesoupItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.stockItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fruitsaladItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spagettiItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spagettiandmeatballsItem, new SetValue(10, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.tomatosoupItem, new SetValue(3, 0.1F));

            FoodModifier.setModifiedSetValue(ItemRegistry.chickenparmasanItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pizzaItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.springsaladItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.porklettucewrapItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fishlettucewrapItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bltItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.leafychickensandwichItem, new SetValue(8, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.leafyfishsandwichItem, new SetValue(8, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.deluxecheeseburgerItem, new SetValue(10, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.delightedmealItem, new SetValue(16, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.onionsoupItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.potatocakesItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.hashItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.braisedonionsItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.heartybreakfastItem, new SetValue(15, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cornonthecobItem, new SetValue(3, 0.15F));

            FoodModifier.setModifiedSetValue(ItemRegistry.cornbreadItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.tortillaItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.nachoesItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.tacoItem, new SetValue(8, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fishtacoItem, new SetValue(8, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.creamedcornItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberrysmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberrypieItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberrysaladItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatestrawberryItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.peanutbutterItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.trailmixItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pbandjItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.peanutbuttercookiesItem, new SetValue(3, 0.15F));

            FoodModifier.setModifiedSetValue(ItemRegistry.grapejellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapesaladItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.raisincookiesItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.picklesItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cucumbersaladItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cucumbersoupItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.vegetarianlettucewrapItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.marinatedcucumbersItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.ricesoupItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.friedriceItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mushroomrisottoItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.curryItem, new SetValue(10, 0.55F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rainbowcurryItem, new SetValue(13, 0.7F));
            FoodModifier.setModifiedSetValue(ItemRegistry.refriedbeansItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bakedbeansItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beansandriceItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chiliItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beanburritoItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.stuffedpepperItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.veggiestirfryItem, new SetValue(8, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.grilledskewersItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.supremepizzaItem, new SetValue(12, 0.7F));
            FoodModifier.setModifiedSetValue(ItemRegistry.omeletItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.hotwingsItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chilipoppersItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.extremechiliItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chilichocolateItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonaideItem, new SetValue(1, 0.0F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonbarItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fishdinnerItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonmeringueItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.candiedlemonItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonchickenItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberrysmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberrypieItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberrymuffinItem, new SetValue(4, 0.25F));

            FoodModifier.setModifiedSetValue(ItemRegistry.pancakesItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberrypancakesItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cherrypieItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatecherryItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cherrysmoothieItem, smoothieSetValue);

            FoodModifier.setModifiedSetValue(ItemRegistry.stuffedeggplantItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.eggplantparmItem, new SetValue(8, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberryicedteaItem, new SetValue(1, 0.0F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chaiteaItem, new SetValue(0, 0.0F));
            FoodModifier.setModifiedSetValue(ItemRegistry.espressoItem, new SetValue(1, 0.0F));
            FoodModifier.setModifiedSetValue(ItemRegistry.coffeeconlecheItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mochaicecreamItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pickledbeetsItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beetsaladItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beetsoupItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bakedbeetsItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.broccolimacItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.broccolindipItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.creamedbroccolisoupItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sweetpotatopieItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.candiedsweetpotatoesItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mashedsweetpotatoesItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.steamedpeasItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.splitpeasoupItem, new SetValue(5, 0.25F));

            FoodModifier.setModifiedSetValue(ItemRegistry.pineapplehamItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pineappleyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.turnipsoupItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.roastedrootveggiemedleyItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bakedturnipsItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gingerbreadItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gingersnapsItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.candiedgingerItem, new SetValue(2, 0.1F));

            FoodModifier.setModifiedSetValue(ItemRegistry.softpretzelandmustardItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spicymustardporkItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spicygreensItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.garlicbreadItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.garlicmashedpotatoesItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.garlicchickenItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.summerradishsaladItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.summersquashwithradishItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.celeryandpeanutbutterItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chickencelerycasseroleItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.peasandceleryItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.celerysoupItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.zucchinibreadItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.zucchinifriesItem, new SetValue(8, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.zestyzucchiniItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.zucchinibakeItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.asparagusquicheItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.asparagussoupItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.walnutraisinbreadItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.candiedwalnutsItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.brownieItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.papayasmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.papayayogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.starfruitsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.starfruityogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.guacamoleItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.creamofavocadosoupItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.avocadoburritoItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.poachedpearItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fruitcrumbleItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pearyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.plumyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.bananasplitItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.banananutbreadItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bananasmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.bananayogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.coconutmilkItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chickencurryItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.coconutshrimpItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.coconutyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.orangechickenItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.orangesmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.orangeyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.peachcobblerItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.peachsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.peachyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.keylimepieItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.limesmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.limeyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.mangosmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.mangoyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pomegranatesmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pomegranateyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.vanillayogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cinnamonrollItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.frenchtoastItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.marshmellowsItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.donutItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatedonutItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.powdereddonutItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.jellydonutItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.frosteddonutItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cactussoupItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.wafflesItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.seedsoupItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.softpretzelItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.jellybeansItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.biscuitItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.creamcookieItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.jaffaItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.friedchickenItem, new SetValue(7, 0.35F));

            FoodModifier.setModifiedSetValue(ItemRegistry.footlongItem, new SetValue(9, 0.55F));
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberryyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cherryyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberryyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapeyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolateyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blackberrycobblerItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.blackberrysmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blackberryyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatemilkItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pumpkinyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberrypieItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberrysmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberryyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cinnamonsugardonutItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.melonyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.kiwismoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.kiwiyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.plainyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.appleyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.saltedsunflowerseedsItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sunflowerwheatrollsItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sunflowerbroccolisaladItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cranberrysauceItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cranberrybarItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.peppermintItem, new SetValue(2, 0.05F));

            FoodModifier.setModifiedSetValue(ItemRegistry.baklavaItem, new SetValue(7, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gummybearsItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.baconmushroomburgerItem, new SetValue(10, 0.65F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fruitpunchItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.meatystewItem, new SetValue(6, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mixedsaladItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pinacoladaItem, new SetValue(2, 0.15F));

            FoodModifier.setModifiedSetValue(ItemRegistry.shepardspieItem, new SetValue(6, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.eggnogItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.custardItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sushiItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gardensoupItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.applejellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.applejellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blackberryjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blackberryjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberryjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.blueberryjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cherryjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cherryjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cranberryjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cranberryjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.kiwijellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.kiwijellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.limejellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.limejellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.mangojellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.mangojellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.orangejellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.orangejellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.papayajellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.papayajellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.peachjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.peachjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pomegranatejellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pomegranatejellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberryjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberryjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.starfruitjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.starfruitjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberryjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberryjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.watermelonjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.watermelonjellysandwichItem, jellySandwichSetValue);

            FoodModifier.setModifiedSetValue(ItemRegistry.cherrysodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.colasodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.gingersodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapesodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.lemonlimesodaItem, new SetValue(3, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.orangesodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.rootbeersodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberrysodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.caramelicecreamItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mintchocolatechipicemcreamItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberryicecreamItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.vanillaicecreamItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gingerchickenItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.oldworldveggiesoupItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spicebunItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gingeredrhubarbtartItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lambbarleysoupItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.honeylemonlambItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pumpkinoatsconesItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beefjerkyItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.ovenroastedcauliflowerItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.leekbaconsoupItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.herbbutterparsnipsItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.scallionbakedpotatoItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.soymilkItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.firmtofuItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.silkentofuItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bamboosteamedriceItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.roastedchestnutItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sweetpotatosouffleItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cashewchickenItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.apricotyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.apricotglazedporkItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.apricotjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.apricotjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.apricotsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.figbarItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.figjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.figjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.figsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.figyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapefruitjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapefruitjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapefruitsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapefruityogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapefruitsodaItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.citrussaladItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pecanpieItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pralinesItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.persimmonyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.persimmonsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.persimmonjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.persimmonjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pistachiobakedsalmonItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.baconwrappeddatesItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.datenutbreadItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.maplesyruppancakesItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.maplesyrupwafflesItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.maplesausageItem, new SetValue(1, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mapleoatmealItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.peachesandcreamoatmealItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cinnamonappleoatmealItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.maplecandiedbaconItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.toastsandwichItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.potatoandcheesepirogiItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.zeppoleItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sausageinbreadItem, new SetValue(16, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatecaramelfudgeItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lavendershortbreadItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beefwellingtonItem, new SetValue(16, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.epicbaconItem, new SetValue(16, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.manjuuItem, new SetValue(4, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chickengumboItem, new SetValue(10, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.generaltsochickenItem, new SetValue(6, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.californiarollItem, new SetValue(4, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.futomakiItem, new SetValue(7, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beansontoastItem, new SetValue(4, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.vegemiteItem, new SetValue(7, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.honeycombchocolatebarItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cherrycoconutchocolatebarItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.fairybreadItem, new SetValue(2, 0.05F));

            FoodModifier.setModifiedSetValue(ItemRegistry.timtamItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.meatpieItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chikorollItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.damperItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.beetburgerItem, new SetValue(16, 0.8F));

            FoodModifier.setModifiedSetValue(ItemRegistry.gherkinItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mcpamItem, new SetValue(10, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.ceasarsaladItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chaoscookieItem, new SetValue(3, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatebaconItem, new SetValue(7, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lambkebabItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.nutellaItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.snickersbarItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spinachpieItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.steamedspinachItem, new SetValue(2, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.vegemiteontoastItem, new SetValue(3, 0.15F));

            FoodModifier.setModifiedSetValue(ItemRegistry.anchovyrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.bassrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.carprawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.catfishrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.charrrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.clamrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.crabrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.crayfishrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.eelrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.frograwItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grouperrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.herringrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.jellyfishrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.mudfishrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.octopusrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.perchrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.scalloprawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.shrimprawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.snailrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.snapperrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.tilapiarawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.troutrawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.tunarawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.turtlerawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.walleyerawItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.greenheartfishItem, fishSetValue);
            //FoodModifier.setModifiedSetValue(ItemRegistry.sardinerawItem, fishSetValue); TODO: 1.11.2
            //FoodModifier.setModifiedSetValue(ItemRegistry.musselrawItem, fishSetValue); TODO: 1.11.2
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofishItem, fishSetValue);

            FoodModifier.setModifiedSetValue(ItemRegistry.clamcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.crabcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.crayfishcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.frogcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.octopuscookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.scallopcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.shrimpcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.snailcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.turtlecookedItem, fishSetValue);

            FoodModifier.setModifiedSetValue(ItemRegistry.appleciderItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bangersandmashItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.batteredsausageItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chorizoItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.coleslawItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.energydrinkItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.friedonionsItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.meatfeastpizzaItem, new SetValue(16, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mincepieItem, new SetValue(6, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.onionhamburgerItem, new SetValue(9, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pepperoniItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pickledonionsItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.porksausageItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.raspberrytrifleItem, new SetValue(6, 0.35F));

            FoodModifier.setModifiedSetValue(ItemRegistry.pumpkinmuffinItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.suaderoItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.randomtacoItem, new SetValue(13, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.turkeyrawItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.turkeycookedItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.venisonrawItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.venisoncookedItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.strawberrymilkshakeItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatemilkshakeItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bananamilkshakeItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cornflakesItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.coleslawburgerItem, new SetValue(10, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.roastchickenItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.roastpotatoesItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sundayroastItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.bbqpulledporkItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lambwithmintsauceItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.steakandchipsItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cherryicecreamItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pistachioicecreamItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.neapolitanicecreamItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.spumoniicecreamItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.almondbutterItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cashewbutterItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chestnutbutterItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cornishpastyItem, new SetValue(6, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cottagepieItem, new SetValue(6, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.croissantItem, new SetValue(4, 0.25F));

            FoodModifier.setModifiedSetValue(ItemRegistry.dimsumItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.friedpecanokraItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gooseberryjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.gooseberryjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.gooseberrymilkshakeItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gooseberrypieItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gooseberrysmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.gooseberryyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.hamsweetpicklesandwichItem, new SetValue(5, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.hushpuppiesItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.kimchiItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mochiItem, new SetValue(3, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.museliItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.naanItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.okrachipsItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.okracreoleItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pistachiobutterItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.ploughmanslunchItem, new SetValue(9, 0.55F));
            FoodModifier.setModifiedSetValue(ItemRegistry.porklomeinItem, new SetValue(9, 0.55F));
            FoodModifier.setModifiedSetValue(ItemRegistry.salmonpattiesItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sausageItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sausagerollItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sesameballItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sesamesnapsItem, new SetValue(1, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.shrimpokrahushpuppiesItem, new SetValue(6, 0.3F));

            FoodModifier.setModifiedSetValue(ItemRegistry.sweetpickleItem, new SetValue(3, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.veggiestripsItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.vindalooItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.applesmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.coconutsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cranberrysmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cranberryyogurtItem, yogurtSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.grapesmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pearsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pearjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.pearjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.plumsmoothieItem, smoothieSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.plumjellyItem, jellySetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.plumjellysandwichItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.honeysandwichItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cheeseontoastItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.tunapotatoItem, new SetValue(7, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolaterollItem, new SetValue(5, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.jamrollItem, new SetValue(4, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.coconutcreamItem, fruitJuiceSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.crackerItem, new SetValue(3, 0.2F));

            FoodModifier.setModifiedSetValue(ItemRegistry.paneerItem, new SetValue(1, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.deluxechickencurryItem, new SetValue(16, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.gravyItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mangochutneyItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.marzipanItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.paneertikkamasalaItem, new SetValue(4, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.peaandhamsoupItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.potatoandleeksoupItem, new SetValue(3, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.yorkshirepuddingItem, new SetValue(2, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.toadintheholeItem, new SetValue(6, 0.35F));

            FoodModifier.setModifiedSetValue(ItemRegistry.hotandsoursoupItem, new SetValue(4, 0.15F));

            FoodModifier.setModifiedSetValue(ItemRegistry.chickenchowmeinItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.kungpaochickenItem, new SetValue(9, 0.5F));

            FoodModifier.setModifiedSetValue(ItemRegistry.charsiuItem, new SetValue(9, 0.5F));

            FoodModifier.setModifiedSetValue(ItemRegistry.sweetandsourchickenItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.baconandeggsItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.biscuitsandgravyItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.applefritterItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sweetteaItem, new SetValue(1, 0.0F));
            FoodModifier.setModifiedSetValue(ItemRegistry.creepercookieItem, new SetValue(3, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.patreonpieItem, new SetValue(5, 0.25F));

            FoodModifier.setModifiedSetValue(ItemRegistry.honeybreadItem, new SetValue(4, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.honeybunItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.honeyglazedcarrotsItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.honeyglazedhamItem, new SetValue(6, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.honeysoyribsItem, new SetValue(9, 0.55F));

            /* TODO: 1.11.2
            FoodModifier.setModifiedSetValue(ItemRegistry.anchovypepperonipizzaItem, new SetValue(9, 0.6F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocovoxelsItem, new SetValue(3, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cinnamontoastItem, jellySandwichSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cornedbeefhashItem, new SetValue(16, 0.8F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cornedbeefItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cottoncandyItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.crackersItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.creeperwingsItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.dhalItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.durianmilkshakeItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.durianmuffinItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.homestylelunchItem, new SetValue(9, 0.6F));

            FoodModifier.setModifiedSetValue(ItemRegistry.hummusItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.ironbrewItem, new SetValue(7, 0.45F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lasagnaItem, new SetValue(7, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lemondrizzlecakeItem, new SetValue(6, 0.4F));
            FoodModifier.setModifiedSetValue(ItemRegistry.meatloafItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.montecristosandwichItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.mushroomlasagnaItem, new SetValue(8, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.musselcookedItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.netherwingsItem, new SetValue(7, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pizzasoupItem, new SetValue(7, 0.1F));
            FoodModifier.setModifiedSetValue(ItemRegistry.poutineItem, new SetValue(4, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.salsaItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.sardinesinhotsauceItem, new SetValue(3, 0.15F));
            FoodModifier.setModifiedSetValue(ItemRegistry.teriyakichickenItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.toastedwesternItem, new SetValue(9, 0.5F));
            FoodModifier.setModifiedSetValue(ItemRegistry.turkishdelightItem, new SetValue(2, 0.1F));*/

            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofeakItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofaconItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofeegItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofuttonItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofickenItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofabbitItem, new SetValue(2, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofurkeyItem, new SetValue(1, 0.05F));
            FoodModifier.setModifiedSetValue(ItemRegistry.rawtofenisonItem, new SetValue(2, 0.05F));

            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofeakItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofaconItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofishItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofeegItem, fishSetValue);
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofuttonItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofickenItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofabbitItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofurkeyItem, new SetValue(3, 0.2F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cookedtofenisonItem, new SetValue(6, 0.35F));

            FoodModifier.setModifiedSetValue(ItemRegistry.carrotcakeItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.holidaycakeItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pumpkincheesecakeItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pavlovaItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.lamingtonItem, new SetValue(4, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cheesecakeItem, new SetValue(5, 0.25F));
            FoodModifier.setModifiedSetValue(ItemRegistry.cherrycheesecakeItem, new SetValue(7, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.pineappleupsidedowncakeItem, new SetValue(6, 0.3F));
            FoodModifier.setModifiedSetValue(ItemRegistry.chocolatesprinklecakeItem, new SetValue(6, 0.35F));
            FoodModifier.setModifiedSetValue(ItemRegistry.redvelvetcakeItem, new SetValue(7, 0.35F));
        }

        // Sapling Growth
        PlantGrowthModification genericSaplingGrowthModification = new PlantGrowthModification().setGrowthTickProbability(Config.saplingRegrowthMultiplier);
        PlantGrowthModule.registerPlantGrowthModifier(BlockPamSapling.class, genericSaplingGrowthModification);

        PlantGrowthModification temperateSaplingGrowthModification = new PlantGrowthModification().setGrowthTickProbability(Config.saplingRegrowthMultiplier).setBiomeGrowthModifier(Type.FOREST, 1).setBiomeGrowthModifier(Type.PLAINS, 1);
        for (Block temperateSapling : FruitRegistry.temperateSaplings.values())
        {
            PlantGrowthModule.registerPlantGrowthModifier(temperateSapling, temperateSaplingGrowthModification);
        }
        PlantGrowthModule.registerPlantGrowthModifier(FruitRegistry.getSapling(FruitRegistry.MAPLE), temperateSaplingGrowthModification);

        PlantGrowthModification warmSaplingGrowthModification = new PlantGrowthModification().setGrowthTickProbability(Config.saplingRegrowthMultiplier).setBiomeGrowthModifier(Type.JUNGLE, 1).setBiomeGrowthModifier(Type.SWAMP, 1);
        for (Block warmSapling : FruitRegistry.warmSaplings.values())
        {
            PlantGrowthModule.registerPlantGrowthModifier(warmSapling, warmSaplingGrowthModification);
        }
        PlantGrowthModule.registerPlantGrowthModifier(FruitRegistry.getSapling(FruitRegistry.CINNAMON), warmSaplingGrowthModification);

        // Fruit Growth
        PlantGrowthModification genericFruitGrowthModification = new PlantGrowthModification().setNeedsSunlight(false).setGrowthTickProbability(Config.treeCropRegrowthMultiplier);
        PlantGrowthModule.registerPlantGrowthModifier(BlockPamFruit.class, genericFruitGrowthModification);

        PlantGrowthModification temperateFruitGrowthModification = new PlantGrowthModification().setNeedsSunlight(false).setGrowthTickProbability(Config.treeCropRegrowthMultiplier).setBiomeGrowthModifier(Type.FOREST, 1).setBiomeGrowthModifier(Type.PLAINS, 1);
        for (Block temperateSapling : FruitRegistry.temperateSaplings.values())
        {
            Block fruitBlock = PamsModsHelper.saplingToFruitBlockMap.get(temperateSapling);
            PlantGrowthModule.registerPlantGrowthModifier(fruitBlock, temperateFruitGrowthModification);
        }
        PlantGrowthModule.registerPlantGrowthModifier(FruitRegistry.getLog(FruitRegistry.MAPLE), temperateSaplingGrowthModification);

        PlantGrowthModification warmFruitGrowthModification = new PlantGrowthModification().setNeedsSunlight(false).setGrowthTickProbability(Config.treeCropRegrowthMultiplier).setBiomeGrowthModifier(Type.JUNGLE, 1).setBiomeGrowthModifier(Type.SWAMP, 1);
        for (Block warmSapling : FruitRegistry.warmSaplings.values())
        {
            Block fruitBlock = PamsModsHelper.saplingToFruitBlockMap.get(warmSapling);
            PlantGrowthModule.registerPlantGrowthModifier(fruitBlock, warmFruitGrowthModification);
        }
        PlantGrowthModule.registerPlantGrowthModifier(FruitRegistry.getLog(FruitRegistry.CINNAMON), warmFruitGrowthModification);

        PlantGrowthModification humidCropGrowthModification = new PlantGrowthModification().setNeedsSunlight(true).setGrowthTickProbability(Config.cropRegrowthMultiplier).setBiomeGrowthModifier(Type.JUNGLE, 1).setBiomeGrowthModifier(Type.SWAMP, 1);
        PlantGrowthModule.registerPlantGrowthModifier(CropRegistry.getCrop(CropRegistry.PINEAPPLE), humidCropGrowthModification);
        PlantGrowthModule.registerPlantGrowthModifier(CropRegistry.getCrop(CropRegistry.SPICELEAF), humidCropGrowthModification);
        PlantGrowthModule.registerPlantGrowthModifier(CropRegistry.getCrop(CropRegistry.CANDLEBERRY), humidCropGrowthModification);
        PlantGrowthModule.registerPlantGrowthModifier(CropRegistry.getCrop(CropRegistry.GRAPE), humidCropGrowthModification);
        PlantGrowthModule.registerPlantGrowthModifier(CropRegistry.getCrop(CropRegistry.KIWI), humidCropGrowthModification);

        PlantGrowthModification desertCropGrowthModification = new PlantGrowthModification().setNeedsSunlight(true).setGrowthTickProbability(Config.cropRegrowthMultiplier).setBiomeGrowthModifier(Type.SANDY, 1);
        PlantGrowthModule.registerPlantGrowthModifier(CropRegistry.getCrop(CropRegistry.CACTUSFRUIT), desertCropGrowthModification);

        /*
         * Bonemeal
         */
        BonemealModification fruitBonemealModification = new BonemealModification()
        {
            @Override
            public IBlockState getNewState(World world, BlockPos pos, IBlockState currentState)
            {
                return currentState.withProperty(BlockPamFruit.AGE, Math.min(currentState.getValue(BlockPamFruit.AGE) + 1, 2));
            }
        };
        BonemealModule.registerBonemealModifier(BlockPamFruit.class, fruitBonemealModification);
    }

    public SetValue SetValue(int h, float s) {
           return new FoodValues(h*2, s*2);
    }
}
