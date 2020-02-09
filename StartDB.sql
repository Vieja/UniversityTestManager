DROP TABLE STUDENCI cascade constraints;
DROP TABLE ZESTAWY cascade constraints;
DROP TABLE PODEJSCIA cascade constraints;
DROP TABLE ZADANIA cascade constraints;
DROP TABLE ZAWARTOSC cascade constraints;
DROP SEQUENCE zestawy_sequence;
DROP SEQUENCE zadania_sequence;

CREATE OR REPLACE SEQUENCE zestawy_sequence START WITH 1;
CREATE SEQUENCE zadania_sequence START WITH 1;

CREATE TABLE STUDENCI
       (INDEKS NUMBER(6) CHECK (99999<INDEKS and INDEKS<1000000) CONSTRAINT PK_STUD PRIMARY KEY,
    IMIE VARCHAR2(15) not null,
	NAZWISKO VARCHAR2(30) not null,
	OCENA_1 VARCHAR2(3) CHECK (OCENA_1 IN ('2.0', '3.0', '3.5', '4.0', '4.5', '5.0')),
    OCENA_2 VARCHAR2(3) CHECK (OCENA_2 IN ('2.0', '3.0', '3.5', '4.0', '4.5', '5.0'))
    );

CREATE TABLE ZESTAWY
       (ID_ZES NUMBER(6) DEFAULT zestawy_sequence.nextval CONSTRAINT PK_ZES PRIMARY KEY,
	NAZWA VARCHAR2(15) not null,
    DATA_EGZ DATE DEFAULT CURRENT_DATE not null,
    TERMIN VARCHAR2(8) not null CHECK (TERMIN IN ('pierwszy', 'drugi', 'trzeci')),
    CONSTRAINT UNIQUE_NAZWA UNIQUE (NAZWA, DATA_EGZ)
    );

CREATE TABLE PODEJSCIA
       (INDEKS NUMBER(6) CONSTRAINT FK_STUD REFERENCES STUDENCI(INDEKS) ON DELETE CASCADE,
    ID_ZES NUMBER(6) CONSTRAINT FK_POD_ZES REFERENCES ZESTAWY(ID_ZES) ON DELETE CASCADE,
    OCENA VARCHAR2(3) CHECK (OCENA IN ('2.0', '3.0', '3.5', '4.0', '4.5', '5.0')),
    CONSTRAINT PK_POD PRIMARY KEY (INDEKS, ID_ZES)
    );

CREATE TABLE ZADANIA
       (ID_ZAD NUMBER(6) DEFAULT zadania_sequence.nextval CONSTRAINT PK_ZAD PRIMARY KEY,
	TRESC VARCHAR2(255) not null,
    PUNKTY NUMBER (3,1) CHECK (PUNKTY>0) not null
    );

CREATE TABLE ZAWARTOSC
       (ID_ZES NUMBER(6) CONSTRAINT FK_ZAW_ZES REFERENCES ZESTAWY(ID_ZES) ON DELETE CASCADE,
    ID_ZAD NUMBER(6) CONSTRAINT FK_ZAD REFERENCES ZADANIA(ID_ZAD) ON DELETE CASCADE,
    CONSTRAINT PK_ZAW PRIMARY KEY (ID_ZES, ID_ZAD)
    );

CREATE BITMAP INDEX oceny_indeks on STUDENCI(OCENA_1, OCENA_2);
CREATE INDEX podejscia_indeks on PODEJSCIA(ID_ZES, INDEKS);
CREATE INDEX zawartosc_indeks on ZAWARTOSC(ID_ZES, ID_ZAD);

INSERT INTO STUDENCI VALUES (135210,'Irena','Jabłońska',null,null);
INSERT INTO STUDENCI VALUES (135211,'Krystian','Lewandowski',null,null);
INSERT INTO STUDENCI VALUES (135212,'Renata','Czerwińska',null,null);
INSERT INTO STUDENCI VALUES (135213,'Joanna','Kaczmarek',null,null);
INSERT INTO STUDENCI VALUES (135214,'Kazimiera','Jaworska',null,null);
INSERT INTO STUDENCI VALUES (135215,'Izabela','Jankowska',null,null);
INSERT INTO STUDENCI VALUES (135216,'Patry','Brzeziński',null,null);
INSERT INTO STUDENCI VALUES (135217,'Stanisława','Szewczyk','2.0',null);
INSERT INTO STUDENCI VALUES (135218,'Zdzisław','Kowalski','4.0',null);
INSERT INTO STUDENCI VALUES (135219,'Kornelia','Michalak',null,null);
INSERT INTO STUDENCI VALUES (135220,'Maria','Sikora',null,null);
INSERT INTO STUDENCI VALUES (135221,'Marek','Kalinowski',null,null);
INSERT INTO STUDENCI VALUES (135222,'Mirosław','Malinowski',null,null);
INSERT INTO STUDENCI VALUES (135223,'Iwona','Woźniak',null,null);
INSERT INTO STUDENCI VALUES (135224,'Monika','Chmielewska',null,null);
INSERT INTO STUDENCI VALUES (135225,'Helena','Dąbrowska',null,null);
INSERT INTO STUDENCI VALUES (135226,'Aneta','Pawlicka',null,null);
INSERT INTO STUDENCI VALUES (135227,'Irena','Wiechczyńska',null,null);
INSERT INTO STUDENCI VALUES (135228,'Kornelia','Wróblewska','2.0',null);
INSERT INTO STUDENCI VALUES (135229,'Wiesław','Włodarczyk','4.5',null);
INSERT INTO STUDENCI VALUES (135230,'Stanisława','Sawicka',null,null);
INSERT INTO STUDENCI VALUES (135231,'Sławomir','Kamiński','3.0',null);
INSERT INTO STUDENCI VALUES (135232,'Zbigniew','Lis',null,null);
INSERT INTO STUDENCI VALUES (135233,'Józef','Wiśniewski','2.0',null);
INSERT INTO STUDENCI VALUES (135234,'Kornelia','Czerwińska','3.0',null);
INSERT INTO STUDENCI VALUES (135235,'Sławomir','Górka',null,null);
INSERT INTO STUDENCI VALUES (135236,'Jarosław','Przybylski','3.5',null);
INSERT INTO STUDENCI VALUES (135237,'Urszula','Wysocka',null,null);
INSERT INTO STUDENCI VALUES (135238,'Stanisława','Adamska',null,null);
INSERT INTO STUDENCI VALUES (135239,'Zdzisław','Kubiak',null,null);
INSERT INTO STUDENCI VALUES (135240,'Joanna','Wróblewska',null,null);
INSERT INTO STUDENCI VALUES (135241,'Łukasz','Czerwiński',null,null);
INSERT INTO STUDENCI VALUES (135242,'Sławomir','Wiechczyński','3.5',null);
INSERT INTO STUDENCI VALUES (135243,'Jerzy','Jabłoński',null,null);
INSERT INTO STUDENCI VALUES (135244,'Jacek','Czerwiński',null,null);
INSERT INTO STUDENCI VALUES (135245,'Bożena','Rutkowska','3.0',null);
INSERT INTO STUDENCI VALUES (135246,'Waldemar','Malinowski','3.0',null);
INSERT INTO STUDENCI VALUES (135247,'Iwona','Górka',null,null);
INSERT INTO STUDENCI VALUES (135248,'Wojciech','Majewski',null,null);
INSERT INTO STUDENCI VALUES (135249,'Ryszard','Makowski','4.5',null);
INSERT INTO STUDENCI VALUES (135250,'Ewa','Włodarczyk',null,null);
INSERT INTO STUDENCI VALUES (135251,'Rafał','Wasilewski',null,null);
INSERT INTO STUDENCI VALUES (135252,'Krzysztof','Piotrowski','3.5',null);
INSERT INTO STUDENCI VALUES (135253,'Wojciech','Mazur',null,null);
INSERT INTO STUDENCI VALUES (135254,'Stanisława','Górka',null,null);
INSERT INTO STUDENCI VALUES (135255,'Mateusz','Jankowski',null,null);
INSERT INTO STUDENCI VALUES (135256,'Mateusz','Czarnecki',null,null);
INSERT INTO STUDENCI VALUES (135257,'Robert','Szymański',null,null);
INSERT INTO STUDENCI VALUES (135258,'Artur','Pawlicki',null,null);
INSERT INTO STUDENCI VALUES (135259,'Jacek','Chmielewski',null,null);
INSERT INTO STUDENCI VALUES (135260,'Wiesława','Ostrowska',null,null);
INSERT INTO STUDENCI VALUES (135261,'Waldemar','Wieczorek',null,null);
INSERT INTO STUDENCI VALUES (135262,'Sławomir','Sadowski',null,null);
INSERT INTO STUDENCI VALUES (135263,'Ryszard','Piotrowski',null,null);
INSERT INTO STUDENCI VALUES (135264,'Jakub','Jasiński',null,null);
INSERT INTO STUDENCI VALUES (135265,'Halina','Wróblewska','3.5',null);
INSERT INTO STUDENCI VALUES (135266,'Tadeusz','Witkowski',null,null);
INSERT INTO STUDENCI VALUES (135267,'Iwona','Szewczyk',null,null);
INSERT INTO STUDENCI VALUES (135268,'Elżbieta','Wasilewska',null,null);
INSERT INTO STUDENCI VALUES (135269,'Krystyna','Wójcik',null,null);
INSERT INTO STUDENCI VALUES (135270,'Bożena','Sikorska',null,null);
INSERT INTO STUDENCI VALUES (135271,'Marianna','Graczyk',null,null);
INSERT INTO STUDENCI VALUES (135272,'Teresa','Przybylska',null,null);
INSERT INTO STUDENCI VALUES (135273,'Helena','Pawlicka',null,null);
INSERT INTO STUDENCI VALUES (135274,'Krystian','Czerwiński',null,null);
INSERT INTO STUDENCI VALUES (135275,'Oliwi','Baran',null,null);
INSERT INTO STUDENCI VALUES (135276,'Mieczysław','Malinowski','4.5',null);
INSERT INTO STUDENCI VALUES (135277,'Krystyna','Kamińska',null,null);
INSERT INTO STUDENCI VALUES (135278,'Zdzisław','Wieczorek',null,null);
INSERT INTO STUDENCI VALUES (135279,'Dawid','Malinowski',null,null);
INSERT INTO STUDENCI VALUES (135280,'Marta','Lis','3.5',null);
INSERT INTO STUDENCI VALUES (135281,'Robert','Zieliński',null,null);
INSERT INTO STUDENCI VALUES (135282,'Mieczysław','Początek','3.5',null);
INSERT INTO STUDENCI VALUES (135283,'Agata','Kowalska','4.0',null);
INSERT INTO STUDENCI VALUES (135284,'Edward','Jaworski','4.0',null);
INSERT INTO STUDENCI VALUES (135285,'Marcin','Kalinowski',null,null);
INSERT INTO STUDENCI VALUES (135286,'Joanna','Wysocka',null,null);
INSERT INTO STUDENCI VALUES (135287,'Andrzej','Chmielewski',null,null);
INSERT INTO STUDENCI VALUES (135288,'Piotr','Urbański',null,null);
INSERT INTO STUDENCI VALUES (135289,'Ewelina','Andrzejewska','4.5',null);
INSERT INTO STUDENCI VALUES (135290,'Alicja','Cieślak',null,null);
INSERT INTO STUDENCI VALUES (135291,'Marzena','Laskowska',null,null);
INSERT INTO STUDENCI VALUES (135292,'Kornelia','Marciniak','4.0',null);
INSERT INTO STUDENCI VALUES (135293,'Marzena','Mazur',null,null);
INSERT INTO STUDENCI VALUES (135294,'Helena','Sadowska',null,null);
INSERT INTO STUDENCI VALUES (135295,'Patrycja','Wojciechowska',null,null);
INSERT INTO STUDENCI VALUES (135296,'Wiktoria','Adamska',null,null);
INSERT INTO STUDENCI VALUES (135297,'Rafał','Adamczyk',null,null);
INSERT INTO STUDENCI VALUES (135298,'Karolina','Brzezińska','4.5',null);
INSERT INTO STUDENCI VALUES (135299,'Artur','Ostrowski',null,null);
INSERT INTO STUDENCI VALUES (135300,'Damian','Wieczorek',null,null);
INSERT INTO STUDENCI VALUES (135301,'Damian','Borkowski','4.0',null);
INSERT INTO STUDENCI VALUES (135302,'Józef','Szymczak','5.0',null);
INSERT INTO STUDENCI VALUES (135303,'Damian','Wróblewski','3.5',null);
INSERT INTO STUDENCI VALUES (135304,'Jadwiga','Pawlak',null,null);
INSERT INTO STUDENCI VALUES (135305,'Roman','Nowicki',null,null);
INSERT INTO STUDENCI VALUES (135306,'Maria','Jakubowska','3.0',null);
INSERT INTO STUDENCI VALUES (135307,'Natalia','Grabowska',null,null);
INSERT INTO STUDENCI VALUES (135308,'Alicja','Dudek','3.5',null);
INSERT INTO STUDENCI VALUES (135309,'Władysław','Zakrzewski',null,null);
INSERT INTO STUDENCI VALUES (135310,'Małgorzata','Sikora',null,null);
INSERT INTO STUDENCI VALUES (135311,'Izabela','Kaczmarek','3.5',null);
INSERT INTO STUDENCI VALUES (135312,'Roman','Kowalczyk',null,null);
INSERT INTO STUDENCI VALUES (135313,'Dariusz','Wiśniewski',null,null);
INSERT INTO STUDENCI VALUES (135314,'Renata','Laskowska',null,null);
INSERT INTO STUDENCI VALUES (135315,'Izabela','Krajewska',null,null);
INSERT INTO STUDENCI VALUES (135316,'Wiesława','Kowalska',null,null);
INSERT INTO STUDENCI VALUES (135317,'Irena','Cieślak',null,null);
INSERT INTO STUDENCI VALUES (135318,'Marzena','Adamska',null,null);
INSERT INTO STUDENCI VALUES (135319,'Barbara','Zielińska',null,null);
INSERT INTO STUDENCI VALUES (135320,'Marian','Wieczorek',null,null);
INSERT INTO STUDENCI VALUES (135321,'Andrzej','Włodarczyk',null,null);
INSERT INTO STUDENCI VALUES (135322,'Damian','Szymański',null,null);
INSERT INTO STUDENCI VALUES (135323,'Mirosław','Grabowski',null,null);
INSERT INTO STUDENCI VALUES (135324,'Martyna','Mazurek',null,null);
INSERT INTO STUDENCI VALUES (135325,'Genowefa','Pietrzak',null,null);
INSERT INTO STUDENCI VALUES (135326,'Janusz','Bąk',null,null);
INSERT INTO STUDENCI VALUES (135327,'Joanna','Głowacka',null,null);
INSERT INTO STUDENCI VALUES (135328,'Małgorzata','Krawczyk',null,null);
INSERT INTO STUDENCI VALUES (135329,'Marek','Wróblewski',null,null);
INSERT INTO STUDENCI VALUES (135330,'Paulina','Wasilewska',null,null);
INSERT INTO STUDENCI VALUES (135331,'Grzegorz','Kubiak','3.5',null);
INSERT INTO STUDENCI VALUES (135332,'Jadwiga','Michalska','3.5',null);
INSERT INTO STUDENCI VALUES (135333,'Mateusz','Kwiatkowski',null,null);
INSERT INTO STUDENCI VALUES (135334,'Grzegorz','Głowacki',null,null);
INSERT INTO STUDENCI VALUES (135335,'Rafał','Wasilewski',null,null);
INSERT INTO STUDENCI VALUES (135336,'Jolanta','Sikora','4.0',null);
INSERT INTO STUDENCI VALUES (135337,'Michał','Sikora',null,null);
INSERT INTO STUDENCI VALUES (135338,'Artur','Zakrzewski',null,null);
INSERT INTO STUDENCI VALUES (135339,'Marcin','Wysocki',null,null);
INSERT INTO STUDENCI VALUES (135340,'Monika','Mazur','3.0',null);
INSERT INTO STUDENCI VALUES (135341,'Czesław','Graczyk','4.0',null);
INSERT INTO STUDENCI VALUES (135342,'Przemysław','Chmielewski','3.0',null);
INSERT INTO STUDENCI VALUES (135343,'Wanda','Zielińska','2.0',null);
INSERT INTO STUDENCI VALUES (135344,'Stefania','Wiśniewska',null,null);
INSERT INTO STUDENCI VALUES (135345,'Piotr','Laskowski',null,null);
INSERT INTO STUDENCI VALUES (135346,'Janusz','Wiśniewski',null,null);
INSERT INTO STUDENCI VALUES (135347,'Genowefa','Jabłońska',null,null);
INSERT INTO STUDENCI VALUES (135348,'Iwona','Bąk',null,null);
INSERT INTO STUDENCI VALUES (135349,'Bożena','Malinowska','3.5',null);
INSERT INTO STUDENCI VALUES (135350,'Janina','Kwiatkowska',null,null);
INSERT INTO STUDENCI VALUES (135351,'Agnieszka','Graczyk',null,null);
INSERT INTO STUDENCI VALUES (135352,'Mateusz','Borkowski',null,null);
INSERT INTO STUDENCI VALUES (135353,'Renata','Krajewska',null,null);
INSERT INTO STUDENCI VALUES (135354,'Waldemar','Szymański',null,null);
INSERT INTO STUDENCI VALUES (135355,'Genowefa','Adamczyk','4.0',null);
INSERT INTO STUDENCI VALUES (135356,'Aneta','Woźniak',null,null);
INSERT INTO STUDENCI VALUES (135357,'Dariusz','Urbański',null,null);
INSERT INTO STUDENCI VALUES (135358,'Maciej','Baran',null,null);
INSERT INTO STUDENCI VALUES (135359,'Danuta','Michalak','3.0',null);
INSERT INTO STUDENCI VALUES (135360,'Zofia','Makowska',null,null);
INSERT INTO STUDENCI VALUES (135361,'Michał','Szymański',null,null);
INSERT INTO STUDENCI VALUES (135362,'Natalia','Brzezińska',null,null);
INSERT INTO STUDENCI VALUES (135363,'Waldemar','Stępień',null,null);

INSERT INTO ZESTAWY VALUES (zestawy_sequence.nextval,'A',to_date('05-12-2019','DD-MM-YYYY'), 'pierwszy');
INSERT INTO ZESTAWY VALUES (zestawy_sequence.nextval,'B',to_date('05-12-2019','DD-MM-YYYY'), 'pierwszy');
INSERT INTO ZESTAWY VALUES (zestawy_sequence.nextval,'C',to_date('05-12-2019','DD-MM-YYYY'), 'pierwszy');

INSERT INTO PODEJSCIA VALUES (135217,1,'2.0');
INSERT INTO PODEJSCIA VALUES (135218,1,'4.0');
INSERT INTO PODEJSCIA VALUES (135228,2,'2.0');
INSERT INTO PODEJSCIA VALUES (135229,2,'4.5');
INSERT INTO PODEJSCIA VALUES (135231,2,'3.0');
INSERT INTO PODEJSCIA VALUES (135233,1,'2.0');
INSERT INTO PODEJSCIA VALUES (135234,3,'3.0');
INSERT INTO PODEJSCIA VALUES (135236,1,'3.5');
INSERT INTO PODEJSCIA VALUES (135242,1,'3.5');
INSERT INTO PODEJSCIA VALUES (135245,3,'3.0');
INSERT INTO PODEJSCIA VALUES (135246,1,'3.0');
INSERT INTO PODEJSCIA VALUES (135249,2,'4.5');
INSERT INTO PODEJSCIA VALUES (135252,3,'3.5');
INSERT INTO PODEJSCIA VALUES (135265,1,'3.5');
INSERT INTO PODEJSCIA VALUES (135276,2,'4.5');
INSERT INTO PODEJSCIA VALUES (135280,2,'3.5');
INSERT INTO PODEJSCIA VALUES (135282,3,'3.5');
INSERT INTO PODEJSCIA VALUES (135283,3,'4.0');
INSERT INTO PODEJSCIA VALUES (135284,1,'4.0');
INSERT INTO PODEJSCIA VALUES (135289,3,'4.5');
INSERT INTO PODEJSCIA VALUES (135292,2,'4.0');
INSERT INTO PODEJSCIA VALUES (135298,2,'4.5');
INSERT INTO PODEJSCIA VALUES (135301,1,'4.0');
INSERT INTO PODEJSCIA VALUES (135302,1,'5.0');
INSERT INTO PODEJSCIA VALUES (135303,2,'3.5');
INSERT INTO PODEJSCIA VALUES (135306,1,'3.0');
INSERT INTO PODEJSCIA VALUES (135308,1,'3.5');
INSERT INTO PODEJSCIA VALUES (135311,3,'3.5');
INSERT INTO PODEJSCIA VALUES (135331,1,'3.5');
INSERT INTO PODEJSCIA VALUES (135332,2,'3.5');
INSERT INTO PODEJSCIA VALUES (135336,2,'4.0');
INSERT INTO PODEJSCIA VALUES (135340,2,'3.0');
INSERT INTO PODEJSCIA VALUES (135341,1,'4.0');
INSERT INTO PODEJSCIA VALUES (135342,3,'3.0');
INSERT INTO PODEJSCIA VALUES (135343,3,'2.0');
INSERT INTO PODEJSCIA VALUES (135349,3,'3.5');
INSERT INTO PODEJSCIA VALUES (135355,3,'4.0');
INSERT INTO PODEJSCIA VALUES (135359,1,'3.0');

INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Oblicz pole koła o promieniu 3 cm', 1);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Masz 30 orzechów, rozdaj po 6 dzieciom. Ile dzieci obdzielisz orzechami?', 2);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Na czym polega konfiguracja VLAN na przełączniku?' , 2);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jakie urządzenia sieciowe mogą wspierać VLAN?', 1);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jak działa CSMA/CA?', 1.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jakie są typy ramek w sieciach wifi?', 1.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jak działa odwzorowanie nazwy domenowej?', 3);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jak wygląda hierarchia domen?', 2);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jak wygląda adres loopback?', 4);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Na czym polega tunelowanie i jak można je zastosować do IPv6?', 5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jaka jest domyślna kolejka w systemie Linux?', 1.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Wyznaczyć wszystkie liczby całkowite n, dla których równanie 2 sin nx = tg x + ctg x ma rozwiązania w liczbach rzeczywistych x', 3);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Rozstrzygnąć, czy istnieją takie dwa przystające sześciany o wspólnym środku, że każda ściana pierwszego sześcianu ma punkt wspólny z każdą ścianą drugiego sześcianu.', 4.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Co to znaczy, że algorytm obliczeniowy jest numerycznie stabilny?', 4);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Sformułować zadanie interpolacyjne Lagrange’a i udowodnić jednoznaczność jego rozwiązania', 2.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Sformułować zadanie interpolacyjne Hermite’a.', 4);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Opisać algorytm wyznaczania naturalnej funkcji sklejanej stopnia trzeciego.', 3);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Opisać algorytm eliminacji Gaussa z pełnym wyborem elementu podstawowego.', 1);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Co to znaczy, że rzeczywista macierz kwadratowa A jest dodatnio określona?', 1);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'W jaki sposób otrzymuje się metody iteracyjne rozwiązywania układów równań liniowych?', 1.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Opisać metodę regula-falsi służącą do wyznaczania pierwiastka równania nieliniowego f(x) = 0.', 2);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Opisać metodę Newtona służącą do rozwiązywania układu równań nieliniowch f(x) = 0.', 3.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Opisać metodę Bairstowa wyznaczania pierwiastków wielomianu.', 5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Podać definicję ciągu Sturma.', 2.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Za pomocą algorytmu Hornera znaleźć iloraz z dzielenia wielomianu w(x) przez dwumian x + 1', 3);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Za pomocą algorytmu Hornera znaleźć wartości wszystkich znormalizowanych pochodnych wielomianu w(x) w punkcie x = 2', 4.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Dla jakich wartości a, b, c funkcja S(x) może być w przedziale [0, 3) naturalną funkcją sklejaną stopnia trzeciego?', 1);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Co to jest shareware?', 2.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Do czego był wykorzystany ENIAC ?', 2);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Czym zajmuje się Electronic Frontier Foundation ?', 3.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Różnica pomiędzy freeware, a oprogramowaniem otwartym.', 3);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Na czym polega koncepcja von Neumana ?', 5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Co to jest EULA ?', 1);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Wyjaśnij pojęcia krój szeryfowy i bezszeryfowy, proporcjonalny i nieproporcjonalny', 3.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jaki mechanizm systemu chroni przed zawłaszczeniem procesora przez przetwarzanie aplikacyjne?', 5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Na czym polega ograniczenie zakresu adresów, dostępnych w czasie przetwarzania aplikacyjnego?', 3.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jak należy zaklasyfikować przerwania będące następstwem dzielenia przez zero?', 2.5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'Jakie mogłyby być skutki udostępniania operacji wejścia-wyjścia w trybie użytkownika dla funkcjonowania systemu komputerowego?', 5);
INSERT INTO ZADANIA VALUES (zadania_sequence.nextval, 'W jaki sposób przekazywane jest sterowanie do jądra systemu operacyjnego?', 1);


INSERT INTO ZAWARTOSC VALUES (1,13);
INSERT INTO ZAWARTOSC VALUES (2,5);
INSERT INTO ZAWARTOSC VALUES (3,8);

commit;