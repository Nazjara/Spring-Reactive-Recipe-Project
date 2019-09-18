package com.nazjara;

import com.nazjara.model.*;
import com.nazjara.repository.CategoryRepository;
import com.nazjara.repository.RecipeRepository;
import com.nazjara.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("onApplicationEvent() triggered");

        Category cat1 = new Category();
        cat1.setDescription("Ukrainian");
        categoryRepository.save(cat1).block();

        Category cat2 = new Category();
        cat2.setDescription("American");
        categoryRepository.save(cat2).block();

        Category cat3 = new Category();
        cat3.setDescription("Italian");
        categoryRepository.save(cat3).block();

        Category cat4 = new Category();
        cat4.setDescription("Mexican");
        categoryRepository.save(cat4).block();

        Category cat5 = new Category();
        cat5.setDescription("Fast Food");
        categoryRepository.save(cat5).block();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1).block();

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2).block();

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3).block();

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4).block();

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5).block();

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6).block();

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7).block();

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8).block();

        Recipe recipe1 = new Recipe();
        recipe1.setId("1");
        recipe1.setDescription("Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).");
        recipe1.setPrepTime(10);
        recipe1.setCookTime(0);
        recipe1.setServings(3);
        recipe1.setSource("Simple Recipes");
        recipe1.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole");
        recipe1.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        try {
            recipe1.setImage(getClass().getClassLoader().getResourceAsStream("static/images/guacamole.jpg").readAllBytes());
        } catch (IOException e) {
            log.error("Error while processing image for recipe", e);
        }

        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.addCategory(categoryRepository.findByDescription("Mexican").block());
        recipe1.addIngredient(new Ingredient("Ancho chili powder", "2", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe1.addIngredient(new Ingredient("Kosher salt", "1/2", unitOfMeasureRepository.findByDescription("Teaspoon").block()));
        recipe1.addIngredient(new Ingredient("Fresh lime juice or lemon juice", "1", unitOfMeasureRepository.findByDescription("Tablespoon").block()));
        recipe1.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", "2", unitOfMeasureRepository.findByDescription("Tablespoon").block()));
        recipe1.addIngredient(new Ingredient("Serrano chiles", "1-2", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe1.addIngredient(new Ingredient("Cilantro", "2", unitOfMeasureRepository.findByDescription("Tablespoon").block()));
        recipe1.addIngredient(new Ingredient("Freshly grated black pepper", "1", unitOfMeasureRepository.findByDescription("Dash").block()));
        recipe1.addIngredient(new Ingredient("Ripe tomato", "1/2", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe1.setNotes(new Notes("Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).\n" +
                "\n" +
                "MAKING GUACAMOLE IS EASY\n" +
                "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato.\n" +
                "\n" +
                "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. You can get creative with homemade guacamole!\n" +
                "\n" +
                "GUACAMOLE TIP: USE RIPE AVOCADOS\n" +
                "The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n" +
                "\n" +
                "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using."));

        Recipe recipe2 = new Recipe();
        recipe2.setId("2");
        recipe2.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
        recipe2.setPrepTime(20);
        recipe2.setCookTime(10);
        recipe2.setServings(5);
        recipe2.setSource("Simple Recipes");
        recipe2.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos");
        recipe2.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");
        try {
            recipe2.setImage(getClass().getClassLoader().getResourceAsStream("static/images/GrilledChickenTacos.jpg").readAllBytes());
        } catch (IOException e) {
            log.error("Error while processing image for recipe", e);
        }

        recipe2.setDifficulty(Difficulty.MODERATE);
        recipe2.addCategory(categoryRepository.findByDescription("Mexican").block());
        recipe2.addIngredient(new Ingredient("Ancho chili powder", "2", unitOfMeasureRepository.findByDescription("Tablespoon").block()));
        recipe2.addIngredient(new Ingredient("Dried oregano", "1", unitOfMeasureRepository.findByDescription("Teaspoon").block()));
        recipe2.addIngredient(new Ingredient("Dried cumin", "1", unitOfMeasureRepository.findByDescription("Teaspoon").block()));
        recipe2.addIngredient(new Ingredient("Sugar", "1", unitOfMeasureRepository.findByDescription("Teaspoon").block()));
        recipe2.addIngredient(new Ingredient("Salt", "1/2", unitOfMeasureRepository.findByDescription("Teaspoon").block()));
        recipe2.addIngredient(new Ingredient("Garlic", "1", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.addIngredient(new Ingredient("Finely grated orange zest", "1", unitOfMeasureRepository.findByDescription("Tablespoon").block()));
        recipe2.addIngredient(new Ingredient("Fresh-squeezed orange juice", "3", unitOfMeasureRepository.findByDescription("Tablespoon").block()));
        recipe2.addIngredient(new Ingredient("Olive oil", "2", unitOfMeasureRepository.findByDescription("Tablespoon").block()));
        recipe2.addIngredient(new Ingredient("Boneless chicken thighs", "4-6", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.addIngredient(new Ingredient("Corn tortillas", "8", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.addIngredient(new Ingredient("Packed baby arugula", "3", unitOfMeasureRepository.findByDescription("Cup").block()));
        recipe2.addIngredient(new Ingredient("Ripe avocados", "2", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.addIngredient(new Ingredient("Radishes", "4", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.addIngredient(new Ingredient("Cherry tomatoes", "1/2", unitOfMeasureRepository.findByDescription("Pint").block()));
        recipe2.addIngredient(new Ingredient("Red onion", "1/4", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.addIngredient(new Ingredient("Roughly chopped cilantro", "1", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.addIngredient(new Ingredient("Sour cream thinned with 1/4 cup milk", "1/2", unitOfMeasureRepository.findByDescription("Cup").block()));
        recipe2.addIngredient(new Ingredient("Lime", "1", unitOfMeasureRepository.findByDescription("Each").block()));
        recipe2.setNotes(new Notes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "\n" +
                "\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "Spicy Grilled Chicken TacosThe ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\n" +
                "\n" +
                "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n" +
                "\n" +
                "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n" +
                "\n" +
                "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!"));

        recipeRepository.save(recipe1).block();
        recipeRepository.save(recipe2).block();
    }
}