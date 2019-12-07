DROP TABLE STUDENCI cascade constraints;
DROP TABLE ZESTAWY cascade constraints;
DROP TABLE PODEJSCIA cascade constraints;
DROP TABLE ZADANIA cascade constraints;
DROP TABLE ZAWARTOSC cascade constraints;

CREATE TABLE STUDENCI
       (INDEKS NUMBER(6) CONSTRAINT PK_STUD PRIMARY KEY,
    IMIE VARCHAR2(15) not null,
	NAZWISKO VARCHAR2(15) not null,
	OCENA_1 VARCHAR2(3) CHECK (OCENA_1 IN ('2.0', '3.0', '3.5', '4.0', '4.5', '5.0')),
    OCENA_2 VARCHAR2(3) CHECK (OCENA_2 IN ('2.0', '3.0', '3.5', '4.0', '4.5', '5.0'))
    );

CREATE TABLE ZESTAWY
       (ID_ZES NUMBER(6) CONSTRAINT PK_ZES PRIMARY KEY,
	NAZWA VARCHAR2(15) not null,
    DATA_UTWORZ DATE not null
    );

CREATE TABLE PODEJSCIA
       (INDEKS NUMBER(6) CONSTRAINT FK_STUD REFERENCES STUDENCI(INDEKS),
    ID_ZES NUMBER(6) CONSTRAINT FK_POD_ZES REFERENCES ZESTAWY(ID_ZES),
	DATA_POD DATE not null,
	TERMIN VARCHAR2(15) not null CHECK (TERMIN IN ('pierwszy', 'drugi', 'trzeci')),
    OCENA VARCHAR2(3) CHECK (OCENA IN ('2.0', '3.0', '3.5', '4.0', '4.5', '5.0')),
    CONSTRAINT PK_POD PRIMARY KEY (INDEKS, ID_ZES)
    );

CREATE TABLE ZADANIA
       (ID_ZAD NUMBER(6) CONSTRAINT PK_ZAD PRIMARY KEY,
	TRESC VARCHAR2(255) not null,
    PUNKTY NUMBER (6,1) not null
    );

CREATE TABLE ZAWARTOSC
       (ID_ZES NUMBER(6) CONSTRAINT FK_ZAW_ZES REFERENCES ZESTAWY(ID_ZES),
    ID_ZAD NUMBER(6) CONSTRAINT FK_ZAD REFERENCES ZADANIA(ID_ZAD)
    );

INSERT INTO STUDENCI VALUES (135210,'Barbara','Mazurek',null,null);
INSERT INTO STUDENCI VALUES (135211,'Anna','Wróbel',null,null);
INSERT INTO STUDENCI VALUES (135212,'Rafał','Sobkowia','2.0',null);
INSERT INTO STUDENCI VALUES (135213,'Krystian','Pawlicki','2.0',null);
INSERT INTO STUDENCI VALUES (135214,'Danuta','Sadowska','5.0',null);
INSERT INTO STUDENCI VALUES (135215,'Edward','Bąk',null,null);
INSERT INTO STUDENCI VALUES (135216,'Adam','Ostrowski','2.0',null);
INSERT INTO STUDENCI VALUES (135217,'Roman','Zieliński','3.0',null);
INSERT INTO STUDENCI VALUES (135218,'Adam','Baran',null,null);
INSERT INTO STUDENCI VALUES (135219,'Agata','Pietrzak','4.5',null);
INSERT INTO STUDENCI VALUES (135220,'Irena','Dudek',null,null);
INSERT INTO STUDENCI VALUES (135221,'Karolina','Zając',null,null);
INSERT INTO STUDENCI VALUES (135222,'Beata','Baranowska',null,null);
INSERT INTO STUDENCI VALUES (135223,'Adam','Szymczak',null,null);
INSERT INTO STUDENCI VALUES (135224,'Wanda','Jasińska',null,null);
INSERT INTO STUDENCI VALUES (135225,'Sławomir','Grabowski',null,null);
INSERT INTO STUDENCI VALUES (135226,'Urszula','Sawicka',null,null);
INSERT INTO STUDENCI VALUES (135227,'Adam','Grabowski','4.0',null);
INSERT INTO STUDENCI VALUES (135228,'Robert','Szymczak',null,null);
INSERT INTO STUDENCI VALUES (135229,'Mieczysław','Kowalski',null,null);
INSERT INTO STUDENCI VALUES (135230,'Justyna','Michalak',null,null);
INSERT INTO STUDENCI VALUES (135231,'Aneta','Kucharska',null,null);
INSERT INTO STUDENCI VALUES (135232,'Jerzy','Laskowski',null,null);
INSERT INTO STUDENCI VALUES (135233,'Henryk','Gajewski',null,null);
INSERT INTO STUDENCI VALUES (135234,'Bożena','Szewczyk','3.5',null);
INSERT INTO STUDENCI VALUES (135235,'Stefania','Tomaszewska','3.0',null);
INSERT INTO STUDENCI VALUES (135236,'Jadwiga','Sadowska',null,null);
INSERT INTO STUDENCI VALUES (135237,'Aleksandra','Kozłowska',null,null);
INSERT INTO STUDENCI VALUES (135238,'Urszula','Wysocka',null,null);
INSERT INTO STUDENCI VALUES (135239,'Martyna','Sikorska',null,null);
INSERT INTO STUDENCI VALUES (135240,'Dorota','Chmielewska',null,null);
INSERT INTO STUDENCI VALUES (135241,'Jadwiga','Piotrowska','5.0',null);
INSERT INTO STUDENCI VALUES (135242,'Patrycja','Zawadzki',null,null);
INSERT INTO STUDENCI VALUES (135243,'Janina','Maciejewska',null,null);
INSERT INTO STUDENCI VALUES (135244,'Teresa','Kwiatkowska','3.0',null);
INSERT INTO STUDENCI VALUES (135245,'Jan','Duda','4.0',null);
INSERT INTO STUDENCI VALUES (135246,'Ewa','Chmielewska',null,null);
INSERT INTO STUDENCI VALUES (135247,'Marian','Pawłowski','3.5',null);
INSERT INTO STUDENCI VALUES (135248,'Kamil','Zieliński','3.5',null);
INSERT INTO STUDENCI VALUES (135249,'Marzena','Graczyk','3.0',null);
INSERT INTO STUDENCI VALUES (135250,'Tomasz','Lewandowski',null,null);
INSERT INTO STUDENCI VALUES (135251,'Stanisław','Lewandowski',null,null);
INSERT INTO STUDENCI VALUES (135252,'Piotr','Szewczyk','3.5',null);
INSERT INTO STUDENCI VALUES (135253,'Waldemar','Majewski',null,null);
INSERT INTO STUDENCI VALUES (135254,'Robert','Pawłowski',null,null);
INSERT INTO STUDENCI VALUES (135255,'Dariusz','Kozłowski',null,null);
INSERT INTO STUDENCI VALUES (135256,'Magdalena','Górska','4.0',null);
INSERT INTO STUDENCI VALUES (135257,'Stefania','Majewska','4.0',null);
INSERT INTO STUDENCI VALUES (135258,'Jadwiga','Gajewska','4.0',null);
INSERT INTO STUDENCI VALUES (135259,'Paweł','Jabłoński','5.0',null);
INSERT INTO STUDENCI VALUES (135260,'Oliwi','Stępień',null,null);
INSERT INTO STUDENCI VALUES (135261,'Beata','Kowalska',null,null);
INSERT INTO STUDENCI VALUES (135262,'Marianna','Urbańska','4.0',null);
INSERT INTO STUDENCI VALUES (135263,'Natalia','Ziółkowska',null,null);
INSERT INTO STUDENCI VALUES (135264,'Wiesław','Tomaszewski',null,null);
INSERT INTO STUDENCI VALUES (135265,'Aleksandra','Wasilewska',null,null);
INSERT INTO STUDENCI VALUES (135266,'Marta','Woźniak','3.5',null);
INSERT INTO STUDENCI VALUES (135267,'Agata','Ziółkowska',null,null);
INSERT INTO STUDENCI VALUES (135268,'Aleksandra','Majewska',null,null);
INSERT INTO STUDENCI VALUES (135269,'Stefania','Jankowska','5.0',null);
INSERT INTO STUDENCI VALUES (135270,'Teresa','Witkowska','3.5',null);
INSERT INTO STUDENCI VALUES (135271,'Natalia','Przybylska',null,null);
INSERT INTO STUDENCI VALUES (135272,'Joanna','Wilk',null,null);
INSERT INTO STUDENCI VALUES (135273,'Marian','Pietrzak','2.0',null);
INSERT INTO STUDENCI VALUES (135274,'Dorota','Adamska','3.5',null);
INSERT INTO STUDENCI VALUES (135275,'Waldemar','Marciniak',null,null);
INSERT INTO STUDENCI VALUES (135276,'Stanisław','Król',null,null);
INSERT INTO STUDENCI VALUES (135277,'Kamil','Jabłoński',null,null);
INSERT INTO STUDENCI VALUES (135278,'Marian','Lis',null,null);
INSERT INTO STUDENCI VALUES (135279,'Dawid','Włodarczyk','2.0',null);
INSERT INTO STUDENCI VALUES (135280,'Patry','Pawlak',null,null);
INSERT INTO STUDENCI VALUES (135281,'Ewa','Szulc',null,null);
INSERT INTO STUDENCI VALUES (135282,'Patry','Nowicki','4.5',null);
INSERT INTO STUDENCI VALUES (135283,'Danuta','Pawlicka',null,null);
INSERT INTO STUDENCI VALUES (135284,'Łukasz','Krawczyk',null,null);
INSERT INTO STUDENCI VALUES (135285,'Justyna','Kaczmarek',null,null);
INSERT INTO STUDENCI VALUES (135286,'Krystyna','Zalewska',null,null);
INSERT INTO STUDENCI VALUES (135287,'Robert','Duda',null,null);
INSERT INTO STUDENCI VALUES (135288,'Edward','Krawczyk',null,null);
INSERT INTO STUDENCI VALUES (135289,'Andrzej','Makowski',null,null);
INSERT INTO STUDENCI VALUES (135290,'Monika','Zawadzki','3.5',null);
INSERT INTO STUDENCI VALUES (135291,'Irena','Malinowska','4.0',null);
INSERT INTO STUDENCI VALUES (135292,'Marzena','Mazur',null,null);
INSERT INTO STUDENCI VALUES (135293,'Monika','Zalewska',null,null);
INSERT INTO STUDENCI VALUES (135294,'Damian','Kaźmierczak',null,null);
INSERT INTO STUDENCI VALUES (135295,'Marzena','Nowakowska','3.0',null);
INSERT INTO STUDENCI VALUES (135296,'Edyta','Kalinowska','5.0',null);
INSERT INTO STUDENCI VALUES (135297,'Jolanta','Kozłowska','5.0',null);
INSERT INTO STUDENCI VALUES (135298,'Sebastian','Kwiatkowski',null,null);
INSERT INTO STUDENCI VALUES (135299,'Helena','Wieczorek',null,null);
INSERT INTO STUDENCI VALUES (135300,'Iwona','Mazurek',null,null);
INSERT INTO STUDENCI VALUES (135301,'Teresa','Krajewska',null,null);
INSERT INTO STUDENCI VALUES (135302,'Roman','Kaczmarek','4.0',null);
INSERT INTO STUDENCI VALUES (135303,'Jakub','Górka',null,null);
INSERT INTO STUDENCI VALUES (135304,'Halina','Nowakowska',null,null);
INSERT INTO STUDENCI VALUES (135305,'Stanisława','Kozłowska',null,null);
INSERT INTO STUDENCI VALUES (135306,'Justyna','Pawlak','3.0',null);
INSERT INTO STUDENCI VALUES (135307,'Patry','Sobczak','4.0',null);
INSERT INTO STUDENCI VALUES (135308,'Kamil','Kucharski',null,null);
INSERT INTO STUDENCI VALUES (135309,'Marzena','Kucharska',null,null);
INSERT INTO STUDENCI VALUES (135310,'Wiktoria','Kozłowska',null,null);
INSERT INTO STUDENCI VALUES (135311,'Grażyna','Laskowska','2.0',null);
INSERT INTO STUDENCI VALUES (135312,'Bożena','Wieczorek','5.0',null);
INSERT INTO STUDENCI VALUES (135313,'Stefania','Wróblewska',null,null);
INSERT INTO STUDENCI VALUES (135314,'Michał','Mazurek','3.5',null);
INSERT INTO STUDENCI VALUES (135315,'Jacek','Czarnecki',null,null);
INSERT INTO STUDENCI VALUES (135316,'Monika','Kołodziej',null,null);
INSERT INTO STUDENCI VALUES (135317,'Barbara','Rutkowska','4.0',null);
INSERT INTO STUDENCI VALUES (135318,'Wojciech','Nowakowski','4.0',null);
INSERT INTO STUDENCI VALUES (135319,'Ewa','Brzezińska',null,null);
INSERT INTO STUDENCI VALUES (135320,'Grzegorz','Krawczyk',null,null);
INSERT INTO STUDENCI VALUES (135321,'Mateusz','Dąbrowski',null,null);
INSERT INTO STUDENCI VALUES (135322,'Wiesław','Majewski','3.5',null);
INSERT INTO STUDENCI VALUES (135323,'Katarzyna','Cieślak',null,null);
INSERT INTO STUDENCI VALUES (135324,'Wanda','Woźniak',null,null);
INSERT INTO STUDENCI VALUES (135325,'Władysław','Kucharski',null,null);
INSERT INTO STUDENCI VALUES (135326,'Izabela','Makowska',null,null);
INSERT INTO STUDENCI VALUES (135327,'Kazimierz','Michalski','4.5',null);
INSERT INTO STUDENCI VALUES (135328,'Zofia','Zając','4.0',null);
INSERT INTO STUDENCI VALUES (135329,'Jan','Graczyk',null,null);
INSERT INTO STUDENCI VALUES (135330,'Józef','Wojciechowski',null,null);
INSERT INTO STUDENCI VALUES (135331,'Janusz','Szymański','3.0',null);
INSERT INTO STUDENCI VALUES (135332,'Janina','Pawłowska',null,null);
INSERT INTO STUDENCI VALUES (135333,'Katarzyna','Gajewska',null,null);
INSERT INTO STUDENCI VALUES (135334,'Waldemar','Michalski',null,null);
INSERT INTO STUDENCI VALUES (135335,'Waldemar','Sikora',null,null);
INSERT INTO STUDENCI VALUES (135336,'Mieczysław','Szymczak',null,null);
INSERT INTO STUDENCI VALUES (135337,'Małgorzata','Pawlicka',null,null);
INSERT INTO STUDENCI VALUES (135338,'Alicja','Kozłowska',null,null);
INSERT INTO STUDENCI VALUES (135339,'Stefania','Czerwińska',null,null);
INSERT INTO STUDENCI VALUES (135340,'Mieczysław','Jankowski',null,null);
INSERT INTO STUDENCI VALUES (135341,'Ryszard','Duda',null,null);
INSERT INTO STUDENCI VALUES (135342,'Renata','Mazurek',null,null);
INSERT INTO STUDENCI VALUES (135343,'Henryk','Kucharski',null,null);
INSERT INTO STUDENCI VALUES (135344,'Janusz','Nowak',null,null);
INSERT INTO STUDENCI VALUES (135345,'Dorota','Malinowska',null,null);
INSERT INTO STUDENCI VALUES (135346,'Marian','Mazurek',null,null);
INSERT INTO STUDENCI VALUES (135347,'Kamil','Gajewski',null,null);
INSERT INTO STUDENCI VALUES (135348,'Natalia','Krawczyk',null,null);
INSERT INTO STUDENCI VALUES (135349,'Mieczysław','Król',null,null);
INSERT INTO STUDENCI VALUES (135350,'Jakub','Makowski',null,null);
INSERT INTO STUDENCI VALUES (135351,'Agnieszka','Rutkowska',null,null);
INSERT INTO STUDENCI VALUES (135352,'Jadwiga','Sawicka',null,null);
INSERT INTO STUDENCI VALUES (135353,'Monika','Grabowska',null,null);
INSERT INTO STUDENCI VALUES (135354,'Wanda','Dąbrowska',null,null);
INSERT INTO STUDENCI VALUES (135355,'Wiesław','Mazurek',null,null);
INSERT INTO STUDENCI VALUES (135356,'Krzysztof','Kamiński',null,null);
INSERT INTO STUDENCI VALUES (135357,'Jolanta','Majewska',null,null);
INSERT INTO STUDENCI VALUES (135358,'Wiesław','Lewandowski',null,null);
INSERT INTO STUDENCI VALUES (135359,'Roman','Duda',null,null);
INSERT INTO STUDENCI VALUES (135360,'Monika','Adamczyk',null,null);
INSERT INTO STUDENCI VALUES (135361,'Daniel','Sobczak',null,null);
INSERT INTO STUDENCI VALUES (135362,'Anna','Krajewska',null,null);
INSERT INTO STUDENCI VALUES (135363,'Iwona','Kaczmarek',null,null);

INSERT INTO ZESTAWY VALUES (1,'A',to_date('05-12-2019','DD-MM-YYYY'));
INSERT INTO ZESTAWY VALUES (2,'B',to_date('05-12-2019','DD-MM-YYYY'));
INSERT INTO ZESTAWY VALUES (3,'C',to_date('05-12-2019','DD-MM-YYYY'));

INSERT INTO PODEJSCIA VALUES (135212,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','2.0');
INSERT INTO PODEJSCIA VALUES (135213,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','2.0');
INSERT INTO PODEJSCIA VALUES (135214,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','5.0');
INSERT INTO PODEJSCIA VALUES (135216,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','2.0');
INSERT INTO PODEJSCIA VALUES (135217,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.0');
INSERT INTO PODEJSCIA VALUES (135219,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.5');
INSERT INTO PODEJSCIA VALUES (135227,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135234,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135235,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.0');
INSERT INTO PODEJSCIA VALUES (135241,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','5.0');
INSERT INTO PODEJSCIA VALUES (135244,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.0');
INSERT INTO PODEJSCIA VALUES (135245,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135247,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135248,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135249,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.0');
INSERT INTO PODEJSCIA VALUES (135252,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135256,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135257,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135258,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135259,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','5.0');
INSERT INTO PODEJSCIA VALUES (135262,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135266,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135269,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','5.0');
INSERT INTO PODEJSCIA VALUES (135270,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135273,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','2.0');
INSERT INTO PODEJSCIA VALUES (135274,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135279,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','2.0');
INSERT INTO PODEJSCIA VALUES (135282,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.5');
INSERT INTO PODEJSCIA VALUES (135290,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135291,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135295,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.0');
INSERT INTO PODEJSCIA VALUES (135296,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','5.0');
INSERT INTO PODEJSCIA VALUES (135297,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','5.0');
INSERT INTO PODEJSCIA VALUES (135302,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135306,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.0');
INSERT INTO PODEJSCIA VALUES (135307,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135311,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','2.0');
INSERT INTO PODEJSCIA VALUES (135312,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','5.0');
INSERT INTO PODEJSCIA VALUES (135314,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135317,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135318,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135322,1,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.5');
INSERT INTO PODEJSCIA VALUES (135327,3,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.5');
INSERT INTO PODEJSCIA VALUES (135328,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','4.0');
INSERT INTO PODEJSCIA VALUES (135331,2,to_date('15-12-2019','DD-MM-YYYY'),'pierwszy','3.0');

INSERT INTO ZADANIA VALUES (1, 'Oblicz pole koła o promieniu 3 cm', 1);
INSERT INTO ZADANIA VALUES (2, 'Masz 30 orzechów, rozdaj po 6 dzieciom. Ile dzieci obdzielisz orzechami?', 2);
INSERT INTO ZADANIA VALUES (3, 'Na czym polega konfiguracja VLAN na przełączniku?' , 2);
INSERT INTO ZADANIA VALUES (4, 'Jakie urządzenia sieciowe mogą wspierać VLAN?', 1);
INSERT INTO ZADANIA VALUES (5, 'Jak działa CSMA/CA?', 1.5);
INSERT INTO ZADANIA VALUES (6, 'Jakie są typy ramek w sieciach wifi?', 1.5);
INSERT INTO ZADANIA VALUES (7, 'Jak działa odwzorowanie nazwy domenowej?', 3);
INSERT INTO ZADANIA VALUES (8, 'Jak wygląda hierarchia domen?', 2);
INSERT INTO ZADANIA VALUES (9, 'Jak wygląda adres loopback?', 4);
INSERT INTO ZADANIA VALUES (10, 'Na czym polega tunelowanie i jak można je zastosować do IPv6?', 5);
INSERT INTO ZADANIA VALUES (11, 'Jaka jest domyślna kolejka w systemie Linux?', 1.5);
INSERT INTO ZADANIA VALUES (12, 'Wyznaczyć wszystkie liczby całkowite n, dla których równanie 2 sin nx = tg x + ctg x ma rozwiązania w liczbach rzeczywistych x', 3);
INSERT INTO ZADANIA VALUES (13, 'Rozstrzygnąć, czy istnieją takie dwa przystające sześciany o wspólnym środku, że każda ściana pierwszego sześcianu ma punkt wspólny z każdą ścianą drugiego sześcianu.', 4.5);
INSERT INTO ZADANIA VALUES (14, 'Co to znaczy, że algorytm obliczeniowy jest numerycznie stabilny?', 4);
INSERT INTO ZADANIA VALUES (15, 'Sformułować zadanie interpolacyjne Lagrange’a i udowodnić jednoznaczność jego rozwiązania', 2.5);
INSERT INTO ZADANIA VALUES (16, 'Sformułować zadanie interpolacyjne Hermite’a.', 4);
INSERT INTO ZADANIA VALUES (17, 'Opisać algorytm wyznaczania naturalnej funkcji sklejanej stopnia trzeciego.', 3);
INSERT INTO ZADANIA VALUES (18, 'Opisać algorytm eliminacji Gaussa z pełnym wyborem elementu podstawowego.', 1);
INSERT INTO ZADANIA VALUES (19, 'Co to znaczy, że rzeczywista macierz kwadratowa A jest dodatnio określona?', 1);
INSERT INTO ZADANIA VALUES (20, 'W jaki sposób otrzymuje się metody iteracyjne rozwiązywania układów równań liniowych?', 1.5);
INSERT INTO ZADANIA VALUES (21, 'Opisać metodę regula-falsi służącą do wyznaczania pierwiastka równania nieliniowego f(x) = 0.', 2);
INSERT INTO ZADANIA VALUES (22, 'Opisać metodę Newtona służącą do rozwiązywania układu równań nieliniowch f(x) = 0.', 3.5);
INSERT INTO ZADANIA VALUES (23, 'Opisać metodę Bairstowa wyznaczania pierwiastków wielomianu.', 5);
INSERT INTO ZADANIA VALUES (24, 'Podać definicję ciągu Sturma.', 2.5);
INSERT INTO ZADANIA VALUES (25, 'Za pomocą algorytmu Hornera znaleźć iloraz z dzielenia wielomianu w(x) przez dwumian x + 1', 3);
INSERT INTO ZADANIA VALUES (26, 'Za pomocą algorytmu Hornera znaleźć wartości wszystkich znormalizowanych pochodnych wielomianu w(x) w punkcie x = 2', 4.5);
INSERT INTO ZADANIA VALUES (27, 'Dla jakich wartości a, b, c funkcja S(x) może być w przedziale [0, 3) naturalną funkcją sklejaną stopnia trzeciego?', 1);
INSERT INTO ZADANIA VALUES (28, 'Co to jest shareware?', 2.5);
INSERT INTO ZADANIA VALUES (29, 'Do czego był wykorzystany ENIAC ?', 2);
INSERT INTO ZADANIA VALUES (30, 'Czym zajmuje się Electronic Frontier Foundation ?', 3.5);
INSERT INTO ZADANIA VALUES (31, 'Różnica pomiędzy freeware, a oprogramowaniem otwartym.', 3);
INSERT INTO ZADANIA VALUES (32, 'Na czym polega koncepcja von Neumana ?', 5);
INSERT INTO ZADANIA VALUES (33, 'Co to jest EULA ?', 1);
INSERT INTO ZADANIA VALUES (34, 'Wyjaśnij pojęcia krój szeryfowy i bezszeryfowy, proporcjonalny i nieproporcjonalny', 3.5);
INSERT INTO ZADANIA VALUES (35, 'Jaki mechanizm systemu chroni przed zawłaszczeniem procesora przez przetwarzanie aplikacyjne?', 5);
INSERT INTO ZADANIA VALUES (36, 'Na czym polega ograniczenie zakresu adresów, dostępnych w czasie przetwarzania aplikacyjnego?', 3.5);
INSERT INTO ZADANIA VALUES (37, 'Jak należy zaklasyfikować przerwania będące następstwem dzielenia przez zero?', 2.5);
INSERT INTO ZADANIA VALUES (38, 'Jakie mogłyby być skutki udostępniania operacji wejścia-wyjścia w trybie użytkownika dla funkcjonowania systemu komputerowego?', 5);
INSERT INTO ZADANIA VALUES (39, 'W jaki sposób przekazywane jest sterowanie do jądra systemu operacyjnego?', 1);


INSERT INTO ZAWARTOSC VALUES (1,13);
INSERT INTO ZAWARTOSC VALUES (2,5);
INSERT INTO ZAWARTOSC VALUES (3,8);