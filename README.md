## Wprowadzenie

**Destroy the Monument** (**DTM**) to plugin do gry
[Minecraft](https://www.minecraft.net/), który wprowadza intensywną minigrę
[PVP](https://pl.wikipedia.org/wiki/Player_versus_player) między co najmniej
dwiema drużynami. Celem rozgrywki jest zniszczenie monumentów wrogiej drużyny, a
jednocześnie obrona własnych struktur. Gra toczy się najczęściej na latających
wyspach, chociaż mapy mogą być różnorodne, a gracze mają pełną swobodę
niszczenia i modyfikowania bloków na arenie, co dodaje strategicznego wymiaru.

### Kluczowe funkcje:

1. **Tryb rozgrywki drużynowej**: Gracze zostają podzieleni na drużyny, a każda
   drużyna ma monument/monumenty, które musi chronić, jednocześnie starając się
   zniszczyć monumenty przeciwników.

2. **Latające wyspy** (**opcjonalnie**): Często areny są osadzone na unoszących
   się w powietrzu platformach, co wymaga od graczy precyzyjnego planowania
   ruchów i ataków.

3. **Niszczenie i budowanie**: Wszystkie bloki na mapie można niszczyć i
   modyfikować, co pozwala graczom na tworzenie fortyfikacji, dróg do ataku lub
   pułapek.

4. **Klasy postaci**: Każdy gracz wybiera klasę, która zapewnia unikalne
   umiejętności oraz specjalny zestaw przedmiotów.

  * Przykłady klas mogą obejmować zwiadowców, łuczników, medyków, obrońców,
    gdzie każda z nich ma swoje zalety na polu bitwy.

5. **Ulepszanie klas**: Za zdobyte w trakcie gry monety (które można uzyskać za
   eliminowanie przeciwników lub niszczenie monumentów), gracze mogą ulepszać
   swoje klasy, uzyskując lepszy ekwipunek lub nowe umiejętności.

### Inspiracja

Plugin **DTM** jest próbą odwzorowania minigry z nieaktywnego już serwera 
[Minecraft](https://www.minecraft.net/), `mclobby.pl`, znanego później również
jako `mcdtm.pl`. Gra była popularna wśród polskiej społeczności
[Minecraft](https://www.minecraft.net/) i charakteryzowała się dynamiczną
rywalizacją oraz angażującą mechaniką.

### Przykładowy gameplay

Aby zobaczyć, jak wyglądała rozgrywka w **DTM**, można obejrzeć
[ten filmik](https://youtu.be/4m8-qcfjIpg) lub
[ten filmik](https://youtu.be/f9zI1OrBWjg), który wprowadza w minigrę i pokazuje
jej zasady w akcji.

> Przed obejrzeniem warto mieć na uwadzę, że filmiki są dość stare (na chwile
publikacji tego opis mają ~8 lat) oraz nie są naszego autorstwa.

## Spis treści

1. [Wprowadzenie](#wprowadzenie)
   * [Kluczowe funkcje](#kluczowe-funkcje)
   * [Inspiracja](#inspiracja)
   * [Przykładowy gameplay](#przykładowy-gameplay)
2. [Instalacja](#instalacja)
   * [Wymagania](#wymagania)
   * [Krok po kroku](#krok-po-kroku)
3. [Współpraca](#współpraca)
   * [Jak możesz pomóc](#jak-możesz-pomóc)
4. [Licencja](#licencja)

## Instalacja

### Wymagania

* **Minecraft**: Wersja 1.8 lub wyższa
* **Platforma serwera**: [Bukkit](https://dev.bukkit.org/) 1.8+ lub
  [Spigot](https://www.spigotmc.org/)/[Paper](https://papermc.io/) 1.8+
* **Java**: Wersja 21 lub wyższa

> Wersja pluginu zawierająca w swojej nazwie słowo `legacy` wymaga wersji
  Minecraft 1.7.10. Rekomendujemy użycie platformy serwera
  [Spigot](https://www.spigotmc.org/) oznaczonego kodową nazwą `b1657` w celu
  uzyskania pełnej kompatybilności z pluginem w wersji legacy.

### Krok po kroku

1. **Pobierz plugin:**
   * Przejdź do sekcji 
     [wydania](https://github.com/kvdpxne/destroy-the-monument/releases) na
     GitHubie.
   * Pobierz najnowszą wersję pliku `.jar` pluginu (np. 
     `destroy-the-monument-0.1.0-bukkit.jar`).

2. **Przygotuj serwer:**
   * Upewnij się, że masz działający serwer gry
     [Minecraft](https://www.minecraft.net/) oparty na platformie
     [Bukkit](https://dev.bukkit.org/), [Spigot](https://www.spigotmc.org/)
     lub [Paper](https://papermc.io/).

3. **Umieść plik na serwerze:**
   * Otwórz folder swojego serwera gry [Minecraft](https://www.minecraft.net/).
   * Przejdź do folderu `plugins`.
   * Skopiuj pobrany plik `.jar` do folderu `plugins`.

4. **Uruchom serwer:**
   * Uruchom lub zrestartuj serwer gry [Minecraft](https://www.minecraft.net/),
     aby załadować plugin.
   * Sprawdź konsolę serwera, aby upewnić się, że plugin został załadowany bez
     błędów.

5. **Konfiguracja (opcjonalna):**
   * Po uruchomieniu serwera, znajdź folder `plugins/destroy-the-monument` w
     swoim katalogu serwera.
   * Edytuj plik konfiguracyjny `settings.yml`, aby dostosować ustawienia
     pluginu do swoich indywidualnych potrzeb.

6. **Testowanie:**
   * Dołącz do serwera i użyj komendy `/dtm help`, aby zobaczyć dostępne
     komendy i upewnić się, że plugin działa prawidłowo.

Teraz jesteś gotowy do zabawy z **Destroy the Monument** (**DTM**). W razie
problemów sprawdź sekcję wsparcie lub skontaktuj się z nami.

## Współpraca

Chcielibyśmy zaprosić społeczność do aktywnego uczestnictwa w rozwoju naszego
pluginu! Istnieje wiele sposobów, w jakie możesz przyczynić się do projektu:

### Jak możesz pomóc:

1. **Zgłaszanie błędów**: Jeśli znajdziesz błąd lub problem z pluginem, prosimy
   o zgłoszenie go na naszej stronie z problemami. Opisz dokładnie, co się
   stało, abyśmy mogli to szybko naprawić.

2. **Propozycje nowych funkcji**: Masz pomysł na nową funkcję lub ulepszenie?
   Podziel się nim! Chętnie wysłuchamy Twoich sugestii.

3. **Przyczynianie się do kodu**: Jeśli masz doświadczenie w programowaniu, 
   zachęcamy do zgłaszania pull requestów. Upewnij się, że kod jest zgodny z
   naszym stylem i dobrze udokumentowany.

4. **Dokumentacja**: Pomóż nam w poprawie dokumentacji. Jeśli zauważysz, że coś
   jest niejasne lub brakuje informacji, prosimy o zgłoszenie tego.

Każda pomoc jest mile widziana, a Twoje pomysły mogą znacząco przyczynić się do
rozwoju pluginu. Dziękujemy za zainteresowanie!

## Licencja

Ten projekt jest objęty licencją **Apache License 2.0**. Możesz używać,
modyfikować i rozpowszechniać ten plugin zgodnie z warunkami tej licencji. Oto
kilka kluczowych punktów:

* Umożliwia wykorzystanie kodu w projektach komercyjnych.
* Wymaga zachowania informacji o prawach autorskich i licencji.
* Umożliwia tworzenie zmodyfikowanych wersji, ale muszą być one również objęte
  licencją **Apache-2.0**.

Szczegóły znajdziesz w pełnej treści licencji w pliku
[LICENSE](https://github.com/kvdpxne/destroy-the-monument/blob/master/LICENSE).
