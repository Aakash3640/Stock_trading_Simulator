

window.onload = () => {
    fetchAllStocks();


    const buyButton = document.getElementById("buyButton");
    const sellButton = document.getElementById("sellButton");

    if (!buyButton || !sellButton) {
        console.error("Buy or Sell button not found!");
        return;
    }

    buyButton.addEventListener("click", (e) => {
        e.preventDefault();
        handleTransaction('BUY');
    });

    sellButton.addEventListener("click", (e) => {
        e.preventDefault();
        handleTransaction('SELL');
    });
};

// Fetch all stocks from the backend
function fetchAllStocks() {
    fetch('http://localhost:8080/api/stocks/all', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to fetch stocks: ' + response.statusText);
        }
        return response.json();
    })
    .then(stocks => {
        console.log(stocks);
        const stockList = document.getElementById("stocks");
        stockList.innerHTML = '';  // Clear the current stock list
        stocks.forEach(stock => {
            const element = document.createElement('li');
            element.textContent = `${stock.symbol} - ${stock.name} - $${stock.price}`;
            stockList.appendChild(element);
        });
    })
    .catch(error => {
        console.error('Error fetching stocks:', error);
        alert('An error occurred while fetching the stock data.');
    });
}

// Handle stock transactions (buy or sell)
function handleTransaction(type) {
    const stockSymbol = document.getElementById("stockSymbol").value;
    const quantity = document.getElementById("quantity").value;
    const price = document.getElementById("price").value;

    if (!stockSymbol || !quantity || !price) {
        alert("All fields are required.");
        return;
    }

    const transactionData = {
        userId: "userID",
        stockSymbol: stockSymbol,
        quantity: parseInt(quantity),
        price: parseFloat(price)
    };

    const url = type === 'BUY'
        ? 'http://localhost:8080/api/transactions/buy'  // Backend API for buy transactions
        : 'http://localhost:8080/api/transactions/sell';  // Backend API for sell transactions

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(transactionData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Transaction failed: ' + response.statusText);
        }

        // Try parsing the response as JSON
        return response.text();  // Get raw text first
    })
    .then(responseText => {
        try {
            // Try parsing the response as JSON
            const data = JSON.parse(responseText);
            alert(`Transaction Successful: ${type} ${quantity} shares of ${stockSymbol}`);
        } catch (e) {
            // If parsing fails, treat it as a plain text message
            alert(`Transaction Message: ${responseText}`);
        }

        fetchAllStocks();  // Refresh stock list after transaction
    })
    .catch(error => {
        console.error('Error handling transaction:', error);
        alert('There was an error processing your transaction.');
    });
}



