# assignment-lc
How to run
How to run
1. Update the absolute file location of raw trades.json in application.properties file
   
  - location of properties file -> assignment-lc/src/main/resources/application.properties
  
2. Make runnable jar using maven
   - mvn package -DskipTests
   - Now run command java -jar pathtojar/upstox-ohlc-0.0.1.jar (Now server will be running)

3. log will be generated in current working directory under folder logs
   
   - each quote event is logged

    2020-06-01 13:17:36,966 INFO c.u.p.QuoteProcessor [main] event OHLC [ sym=XXDGXXBTtradesSize=485, open=8.8E-7, high=8.8E-7, low=8.4E-7, close=0.0, quantity=1.5704068765152458E7, barNum=8]

    - each bar event is logged
    
    
    2020-06-01 13:17:35,807 INFO c.u.p.QuoteProcessor [scheduling-1] bar close event before OHLC [ sym=GNOXBTtradesSize=382, open=0.00333, high=0.00356, low=0.00326, close=0.00337, quantity=1451.6316899099984, barNum=7]   
    
   
  4. Stats are exposed via mbean 


