**Vysoké učení technické v Brně,**  
**Fakulta informačních technologií**  
Arthur Nácar (xnacar00)  
17. března 2019

# Dokumentace projektu IPK <br> Klient pro OpenWeatherMap API 
## Úvod
Jednoduchá konzolová aplikace, která dokáže získat meteorologická data ze serveru OpenWeaterMap.org a následně některé z nich vypíše na standardní výstup. Informace, které se vypisují jsou teplota, oblačnost, vlhkost vzduchu, tlak, rychlost a směr větru.
## Implementační detaily
### Příklad spuštění
* pomocí Makefilu  
  `$ make run api_key=<API klíč> city=<Město>`
* přímé spuštění  
 `$ java Main $(api_key) $(city)`

### Formát výstupu
```
Prague  
overcast clouds  
temp:28.5°C  
humidity:89% 
preassure:1013 hPa  
wind-speed: 7.31km/h  
wind-deg: 187 
```
### Implementace
Implementace probíhala v jazyku Java ve verzi 8. Server je schopen zprostředkovat data ve formátu JSON a XML, nicméně jako formát odpovědi bylo zvoleno XML, jelikož jazyk Java nabízí vestavěnou knihovnu pro práci s tímto formátem.  
Program pro spuštění kontroluje neprázdnost API klíče a destinace, ze které má být vypsáno počasí.  
Veškerou komunikaci se serverem zajišťuje metoda `loadContent()`, která pomocí soketů zajišťuje HTTP komunikaci se serverem. Jako první se odešle GET request podle standardu HTTP/1.1. Dále se čeká na odpověď ze serveru. Z prvního řádku odpovědi je získán návratový kód a pokud je jiný než 200, je tento kód vypsán na standartní chybový výstup a program je ukončen. Ze třetího řádku odpovědi je abstrahován počet znaků XML výstupu. Na dvanáctém řádku odpovědi se nachází XML data, jejichž délka byla popsána. Data jsou po znacích načítána do řetězce znaků. Při úspěšném načtení je soket uzavřen a funkce vrací řetězec znaků s XML daty, které jsou určeny k následnému zpracování.  
Výsledek předchozí funkce je parsován a předkládán funkci `printResult()`, která abstrahuje potřebné údaje ze stromové struktury XML souboru a vypíše je na strandardní výstup.

## Zdroje
OpenWeatherMap API, [https://openweathermap.org/current](https://openweathermap.org/current)