package ru.siblion.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.siblion.repository.ingredient.IngredientRepo;
import ru.siblion.repository.taco.TacoRepo;
import ru.siblion.tacos.Ingredient;
import ru.siblion.tacos.Ingredient.Type;
import ru.siblion.tacos.Order;
import ru.siblion.tacos.Taco;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private  final IngredientRepo ingredientRepo;
    private TacoRepo designRepo;

    /**
     * Это аттрибут сессии, поэтому он должен быть доступным для множества
     * запросов. Используем также @SessionAttributes("order")
     */
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @Autowired
    public DesignTacoController(IngredientRepo ingredientRepo,
        TacoRepo designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    /**
     * Возвращает форму заполнения. Добавление в модель объекта Taco
     * контролируем вручную.
     */
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        System.out.println("[selected data: "+ingredients+"]");

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        if (!model.containsAttribute("design")){
            log.info("[add in model new Taco object]");
            model.addAttribute("design", new Taco());
        }
        return "design";
    }

    /**
     * Если все поля заполнены корректно, то произойдет переход на страницу
     * оформления заказа. Иначе переход снова на страницу заполнения формы.
     *
     * The Order parameter is annotated with @ModelAttribute to indicate that its value should
     * come from the model and that Spring MVC shouldn’t attempt to bind request parameters to it.
     */
    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors,
                                BindingResult binding, RedirectAttributes attr,
                                  @ModelAttribute Order order) {
        log.info("[Taco from form: "+design+"]");
        if (errors.hasErrors()){
            // Каждому объекту модели возвращяется объект BindingResult
            attr.addFlashAttribute("org.springframework.validation.BindingResult.design", binding);
            attr.addFlashAttribute("design", design);
            return "redirect:design";
        }
        Taco saved = designRepo.save(design);
        order.addTacos(saved);
        log.info("[order from processDesign: " + order+"]");
        return "redirect:/orders/current";
    }

    /**
     * Возвращает ингридиенты указанного типа Type
     * @param ingredients - список всех ингридиентов
     */
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        List<Ingredient> list = ingredients.stream().filter(ingrid -> ingrid.getType().equals(type)).collect(Collectors.toList());
        return list;
    }
}