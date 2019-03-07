#IDS - proj
###ZADÁNÍ:
Úkolem je vytvoření serveru komunikujícího prostřednictvím protokolu HTTP, který bude poskytovat různé informace o systému. Server bude naslouchat na zadaném portu a podle url bude vracet požadované informace. Server musí správně zpracovávat hlavičky HTTP a vytvářet správné HTTP odpovědi. Typ odpovědi může být text/plain, text/html, či application/json. Komunikace se serverem by měla být možná jak pomocí webového prohlížeče, tak nástroji typu wget a curl.

Server bude spustitelný pomocí Makefile příkazem:

`make run port=12345`

kde `12345` je číslo portu na kterém server naslouchá. Zjistěte si, jak se používají takto definované argumenty v Makefile. Server bude možné ukončit pomocí `CTRL+C`. Server bude umět zpracovat následující tři typy dotazů:


1. http://servername/hostname:12345
Vrací síťové jméno počítače včetně domény, například:

merlin.fit.vutbr.cz


2. http://servername/cpu-name:12345
Vrací informaci o procesoru, například:

Intel(R) Xeon(R) CPU E5-2640 0 @ 2.50GHz


3. http://servername/load:12345
Vrací aktuální informace o zátěži. Tento vyžaduje výpočet z hodnot uvedených v soubor /proc/stat (viz odkaz níže). Výsledek je například:

65%



Poslední příkaz (load) bude také mít nepovinnou volbu refresh=X, která zajistí, že prohlížeč si bude obnovovat informaci každých X vteřin. Například:
http://servername/load?refresh=5:12345



Potřebné informace pro odpověď lze v systému získat pomocí některých příkazů systému (uname, lscpu) a/nebo ze souborů v adresáři /proc. 



Pro implementaci serveru je nutné využít knihovny soketů. Není přípustné využívat knihovny pro zpracování HTTP a podobně - cílem je lightweight server, který má minimum závislostí. 


###HODNOCENÍ
Hodnotí se "kvalita" implementace (3/4 hodnocení), tedy:
splnění zadání a funkčnost implementace
jednoduchost kódu
množství závislostí na dalších knihovnách (rozumný balanc mezi tím co si napsat sám a co použít z knihoven)

Dále se pak hodnotí dokumentace (1/4) hodnocení.




###DOPORUČENÍ
Zadání nepostihuje ani nemůže postihovat veškeré možnosti, které Vás napadnou při řešení nabo mohou nastat. V takovém případě je nutné navrhnou přijatelné a logické řešení. V případě, že si řešením nejste jisti se optejte prostřednictvím fóra.


###ODKAZY
Protokol HTTP RFC7231 - https://tools.ietf.org/html/rfc7231
Výpočet CPU Load: https://stackoverflow.com/questions/23367857/accurate-calculation-of-cpu-usage-given-in-percentage-in-linux <https://stackoverflow.com/questions/23367857/accurate-calculation-of-cpu-usage-given-in-percentage-in-linux>