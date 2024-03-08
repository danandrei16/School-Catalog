# School-Catalog

Despre modul de testare:
Testare 1: metodele din prima cerinta
Testare 2: cele 4 interfate Swing, prima testare sa fie comentata ca sa se poate valida notele din interfata
Testare 3: Interfata Login

Sablonul Visitor
Visitor: Clasa ScoreVisitor
- clasa Tuple de 3 parametrii generici (+ metode getter pentru cei 3 parametrii)
- 2 dictionare cu partialGrades si examGrades
- in ele introduc notele in clasa Test
- campul catalog pentru a avea acces la cursuri si pentru a notifica parintii
- visit(Assistant) : parcurg toate cursurile, toate grupele, iar pentru grupele care au asistentul respectiv, parcurg
toti studentii, iar ptr fiecare voi adauga nota "partial" in 2 cazuri. Daca studentul deja are nota "exam" trecuta in
catalog, voi trece si nota "partial". Altfel creez Grade-ul pentru student si las campul "examGrade" gol. Apoi
apelez notifyObservers pentru a anunta parintii de nota de parcurs trecuta in catalog
- visit(Teacher) : parcurg cursurile si daca e profesorul respectiv parcurg toti studentii si procedez similar
(testul json are un singur curs ptr fiecare prof/asistent, deci for-ul e cam irelevant, l-am facut pana sa primim testele)

Element: Clasele Teacher, Assistant
- metoda accept apeleaza metoda visit respectiva

Sablonul Factory
- pot sa declar orice User, cu primul parametru de tipul "Student", etc.

Sablonul Observer:
Observer: Clasa Parent
- lista de notificari + metoda de adaugare a unei notificari
Subject: Clasa Catalog
- metode de adaugare/stergere observator din lista de observatori
- notifyObservers(Grade) -> parcurg lista de observatori pentru a gasi parintele studentului caruia ii este asociat
Grade-ul (daca gasesc tatal, notific si mama implicit, daca exista in baza de date si invers). Folosesc o variabila
Grade tip auxiliar pentru ca altfel se modificau notificarile odata cu adaugarea celei de a 2-a note. Creez notificarea
si o transmit parintelui/parintilor, daca acestia exista.

Sablonul Singleton pentru Catalog:
- metoda getInstance() -> creeaza un catalog unic

Sablonul Strategy:
- metoda getBestStudent: parcurg lista de grade-uri si returnez studentul respectiv notei cele mai mari(pentru cele 3
cazuri, cele 3 clase care mostenesc BestScoreStrategy

Sablonul Memento
- face backup la note si undo revine la notele anterioare

Sablonul Builder:
- folosit pentru a crea cursurile

Despre interfete:
Am folosit layout-uri Border(pentru a organiza panelurile) si Grid(de obicei pentru o lista de studenti, cursuri, etc.)
StudentPage: un panel pentru titlu, un panel contine numele si grupa studentului (am adaugat campul ID in Student si
l-am setat) si un panel cu lista de cursuri, de tip butoane. La apasare se afiseaza detalii extra despre curs si notele
studentului. Odata apasat un curs nu poate fi apasat iar pana la inchiderea paginii.

Teacher/Assistant Page: Panel titlu si panel cu numele si lista de cursuri (desi e doar unul in teste). La apasarea
butonului de curs se afiseaza lista de note care trebuie validate prin butonul Validate. Se pot observa notele in
pagina de student si notificarea noua din parent page.

Parent Page: panel titlu si apoi un buton care va afisa lista notificarilor primite.

![Screen Recording - Mar 8, 2024](https://github.com/danandrei16/School-Catalog/assets/94062909/33e4f865-2379-406f-9888-c76cb37b0df3)


Login Page: Daca scriu numele complet al unui user si apas pe buton, se va deschide pagina lui, indiferent de tipul
de user

![Screen Recording - Mar 8, 2024 (1)](https://github.com/danandrei16/School-Catalog/assets/94062909/1ad798a0-b8d5-4fba-8b5f-a7a494d1fca7)

