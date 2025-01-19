package Stock_Simulation.Controller;

import Stock_Simulation.Model.Stock;
import Stock_Simulation.Service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/all")
    public List<Stock> getAllStocks() {
        System.out.println("Inside getAllStocks method");
        return stockService.getAllStocks();
    }

    @RequestMapping("/**")
    public String handleRequest(HttpServletRequest request) {
        return "Received request for: " + request.getRequestURI();
    }
}
