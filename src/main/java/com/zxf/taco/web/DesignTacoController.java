package com.zxf.taco.web;

import com.zxf.taco.Ingredient;
import com.zxf.taco.Ingredient.Type;
import com.zxf.taco.Order;
import com.zxf.taco.Taco;
import com.zxf.taco.data.IngredientRepository;
import com.zxf.taco.data.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
}
