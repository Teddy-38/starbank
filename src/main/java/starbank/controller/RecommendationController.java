package starbank.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starbank.model.BankProduct;


import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {



    @GetMapping("/{userId}")
    public List<BankProduct> getRecommendationsForUser(@PathVariable long userId) {
        return RecommendationService.getRecommendations(userId);
    }
}