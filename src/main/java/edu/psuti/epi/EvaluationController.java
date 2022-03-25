package edu.psuti.epi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EvaluationController {

    @GetMapping("/evaluate")
    public Evaluation perform(@RequestParam("size") int size,
                              @RequestParam("runs") int runs,
                              @RequestParam(value = "scale",
                                            required = false,
                                            defaultValue = "6") int scale) {
        return PerformanceMath.evaluate(size, runs, scale);
    }
}
