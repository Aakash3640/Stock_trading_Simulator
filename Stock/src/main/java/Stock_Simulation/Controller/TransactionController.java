package Stock_Simulation.Controller;

import Stock_Simulation.Model.Portfolio;
import Stock_Simulation.Model.Transaction;
import Stock_Simulation.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private StockService stockService;


    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestBody Transaction transaction) {
        try {

            String userId = String.valueOf(transaction.getId());
            String stockSymbol = transaction.getStockSymbol();
            int quantity = transaction.getQuantity();
            double price = transaction.getPrice();


            String result = stockService.buyStock(userId, stockSymbol, quantity, price);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing the buy transaction.");
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestBody Transaction transaction) {
        try {
            String userId = String.valueOf(transaction.getId());
            String stockSymbol = transaction.getStockSymbol();
            int quantity = transaction.getQuantity();
            double price = transaction.getPrice();

            String result = stockService.sellStock(userId, stockSymbol, quantity, price);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing the sell transaction.");
        }
    }




}
