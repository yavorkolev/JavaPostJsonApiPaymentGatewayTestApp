Java test app: "JavaPostRequest" no functions only one and no classes it's simple console application
IntelliJ IDEA project for:  JSON API Rails 5 API payment gateway
First must set up payment gateway loccaly from: https://github.com/eMerchantPay/codemonsters_api_full

Application is a simple console app with an option to read needed request(test files) with parameters from files, the content of the files must be strict formatted the file content is not validated!
The app validate the existing of the folders: user.dir/ JavaPostRequest/Tests  and user.dir/ JavaPostRequest/Tests/RequestsParams, the folder structure is:
 Project root folder: "JavaPostRequest" // Contain the whole project
   Test folder: "Tests" // Contain log file/s from app execution (example: LogFileTestResults_Tue Apr 28 14:35:58 EEST 2020_.txt) and  "RequestsParams" folder
    RequestsParams folders contains all test with needed parameters STRUCTURED! API
        The content is structured each parameter on new  row: Row-1: API endpoint, Row-2: transaction type, Row-3: request header authentication, Row-4: request body 
        For example: ValidPaymentTransactionRequestParams.txt
        ----------------------------------------------------------------------------------------------
            http://localhost:3001
            /payment_transactions
            Basic Y29kZW1vbnN0ZXI6bXk1ZWNyZXQta2V5Mm8ybw==
            {"payment_transaction":{"card_number":"4200000000000000","cvv":"123","expiration_date":"06/2019","amount":"500","usage":"Coffeemaker","transaction_type":"sale","card_holder":"Panda Panda","email":"panda@example.com","address":"Panda Street, China"}}
        ----------------------------------------------------------------------------------------------
The application features:
  Execute all tests from "Tests" folder with option to repeat all tests again if press "Y" or "y" at the end of all tests to repeat all tests without stop if you want
  Adding new tests with different parameters without changing the code, only the number of parameters must be the same!
  Each execution of the program generates a test report(log file) with all tests results and with actual date and time of execution
  Each execution of the program generates console output with proper colored the needed response results: response code and status approved or not 
  