import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * =============================================================
 * Philippine Event Food Planner 
 * Event Planning System (Multi-Event Support)
 * Renamed to: FoodPlannerFinal
 * =============================================================
 */
public class FoodPlannerFinal {

    // --- Enums ---
    enum Category {
        SOUP_SABAW("Soup / Sabaw"),
        MAIN_ULAM("Main / Ulam"),
        GRILLED_IHAW("Grilled / Ihaw"),
        RICE_KANIN("Rice / Kanin"),
        DESSERT_KAKANIN("Dessert / Kakanin"),
        DRINKS_INUMIN("Drinks / Inumin");

        private final String label;
        Category(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    // --- Data Classes ---
    static class Dish {
        String name;
        Category category;
        int servings;
        String note;

        Dish(String name, Category category, int servings) {
            this.name     = name;
            this.category = category;
            this.servings = servings;
            this.note     = "";
        }
    }

    static class Event {
        String name;
        LocalDate date;
        String type;
        int guestCount;
        String venue;
        List<Dish> menu;

        Event(String name, LocalDate date, String type, int guestCount, String venue) {
            this.name       = name;
            this.date       = date;
            this.type       = type;
            this.guestCount = guestCount;
            this.venue      = venue;
            this.menu       = new ArrayList<>();
        }

        String formattedDate() {
            return date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        }
    }

    // --- Built-in Filipino Food Catalog (All 43 Dishes) ---
    static List<Dish> buildCatalog() {
        List<Dish> c = new ArrayList<>();
        // Soup / Sabaw
        c.add(new Dish("Sinigang na Baboy",    Category.SOUP_SABAW,       15));
        c.add(new Dish("Sinigang na Isda",     Category.SOUP_SABAW,       12));
        c.add(new Dish("Nilaga",               Category.SOUP_SABAW,       15));
        c.add(new Dish("Bulalo",               Category.SOUP_SABAW,        8));
        c.add(new Dish("Tinola",               Category.SOUP_SABAW,       12));
        // Main / Ulam
        c.add(new Dish("Lechon Baboy",         Category.MAIN_ULAM,        30));
        c.add(new Dish("Adobong Manok",        Category.MAIN_ULAM,        15));
        c.add(new Dish("Adobo sa Puti",        Category.MAIN_ULAM,        15));
        c.add(new Dish("Kare-kare",            Category.MAIN_ULAM,        15));
        c.add(new Dish("Pancit Bihon",         Category.MAIN_ULAM,        25));
        c.add(new Dish("Pancit Canton",        Category.MAIN_ULAM,        25));
        c.add(new Dish("Pancit Palabok",       Category.MAIN_ULAM,        20));
        c.add(new Dish("Crispy Pata",          Category.MAIN_ULAM,        10));
        c.add(new Dish("Dinuguan",             Category.MAIN_ULAM,        12));
        c.add(new Dish("Paksiw na Bangus",     Category.MAIN_ULAM,        10));
        c.add(new Dish("Bistek Tagalog",       Category.MAIN_ULAM,        10));
        c.add(new Dish("Pork Menudo",          Category.MAIN_ULAM,        15));
        c.add(new Dish("Caldereta",            Category.MAIN_ULAM,        15));
        c.add(new Dish("Mechado",              Category.MAIN_ULAM,        15));
        c.add(new Dish("Afritada",             Category.MAIN_ULAM,        15));
        c.add(new Dish("Pinakbet",             Category.MAIN_ULAM,        12));
        c.add(new Dish("Monggo Guisado",       Category.MAIN_ULAM,        15));
        c.add(new Dish("Bicol Express",        Category.MAIN_ULAM,        12));
        c.add(new Dish("Laing",                Category.MAIN_ULAM,        12));
        c.add(new Dish("Ginataang Langka",     Category.MAIN_ULAM,        12));
        // Grilled / Ihaw
        c.add(new Dish("Lechon Manok",         Category.GRILLED_IHAW,      8));
        c.add(new Dish("Inihaw na Liempo",     Category.GRILLED_IHAW,     12));
        c.add(new Dish("Inihaw na Pusit",      Category.GRILLED_IHAW,      8));
        // Rice / Kanin
        c.add(new Dish("Steamed Rice",         Category.RICE_KANIN,       30));
        c.add(new Dish("Sinangag",             Category.RICE_KANIN,       20));
        // Dessert / Kakanin
        c.add(new Dish("Leche Flan",           Category.DESSERT_KAKANIN,  12));
        c.add(new Dish("Halo-Halo",            Category.DESSERT_KAKANIN,  10));
        c.add(new Dish("Maja Blanca",          Category.DESSERT_KAKANIN,  15));
        c.add(new Dish("Sapin-Sapin",          Category.DESSERT_KAKANIN,  20));
        c.add(new Dish("Kutsinta",             Category.DESSERT_KAKANIN,  20));
        c.add(new Dish("Biko",                 Category.DESSERT_KAKANIN,  20));
        c.add(new Dish("Puto Bumbong",         Category.DESSERT_KAKANIN,  20));
        c.add(new Dish("Bibingka",             Category.DESSERT_KAKANIN,  15));
        c.add(new Dish("Palitaw",              Category.DESSERT_KAKANIN,  20));
        // Drinks / Inumin
        c.add(new Dish("Calamansi Juice",      Category.DRINKS_INUMIN,    30));
        c.add(new Dish("Gulaman",              Category.DRINKS_INUMIN,    30));
        c.add(new Dish("Buko Juice",           Category.DRINKS_INUMIN,    20));
        c.add(new Dish("Softdrinks",           Category.DRINKS_INUMIN,    30));
        return c;
    }

    // --- App State ---
    static Scanner sc = new Scanner(System.in);
    static List<Event> events = new ArrayList<>(); 
    static Event activeEvent;                      
    static List<Dish> catalog = buildCatalog();

    public static void main(String[] args) {
        printBanner();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ", 0, 6);
            switch (choice) {
                case 1 -> manageEvents();
                case 2 -> browseAndAddDishes();
                case 3 -> viewMenu();
                case 4 -> addCustomDish();
                case 5 -> printEventSummary();
                case 6 -> {
                    System.out.println("\nSalamat! Mabuhay!");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void printBanner() {
        System.out.println("==================================================");
        System.out.println("         Philippine Event Food Planner            ");
        System.out.println("==================================================");
    }

    static void printMainMenu() {
        System.out.println("\n------------------ MAIN MENU -------------------");
        if (activeEvent != null) {
            System.out.printf("  Active Event: %s -- %s%n",
                activeEvent.name, activeEvent.formattedDate());
            System.out.printf("  Menu Items:   %d dish(es)%n", activeEvent.menu.size());
            System.out.printf("  Total Events: %d%n", events.size());
        } else {
            System.out.println("  No Active Event. Please set up an event first.");
        }
        System.out.println("--------------------------------------------------");
        System.out.println("  1. Manage Events (Create New / Switch Event)");
        System.out.println("  2. Browse Catalog & Add Dishes to Menu");
        System.out.println("  3. View Current Menu");
        System.out.println("  4. Add Custom Dish");
        System.out.println("  5. Print Event Summary");
        System.out.println("  6. Exit");
        System.out.println("--------------------------------------------------");
    }

    static void manageEvents() {
        printHeader("Event Management");
        
        if (!events.isEmpty()) {
            System.out.println("  1. Create a New Event");
            System.out.println("  2. Switch to an Existing Event");
            int choice = readInt("Select option: ", 1, 2);
            
            if (choice == 2) {
                System.out.println("\n  Available Events:");
                for (int i = 0; i < events.size(); i++) {
                    System.out.printf("     %d. %s (%s)%n", i+1, events.get(i).name, events.get(i).formattedDate());
                }
                int evIdx = readInt("Select event number to load: ", 1, events.size()) - 1;
                activeEvent = events.get(evIdx);
                System.out.println("\n  Successfully switched to: " + activeEvent.name);
                return;
            }
        }

        System.out.println("\n  -- Creating New Event --");
        String name = readString("Event name: ");

        LocalDate date = null;
        while (date == null) {
            String input = readString("Event date (YYYY-MM-DD): ");
            try { date = LocalDate.parse(input); }
            catch (DateTimeParseException e) {
                System.out.println("  Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.println("  Event types:");
        String[] types = { "Fiesta", "Birthday", "Wedding", "Christmas", "Baptism", "Reunion", "Despedida", "Debut", "Other" };
        for (int i = 0; i < types.length; i++) System.out.printf("     %d. %s%n", i+1, types[i]);
        int ti = readInt("  Select type: ", 1, types.length) - 1;

        int guests = readInt("Number of guests: ", 1, 9999);
        
        String venue = "";
        while (true) {
            venue = readString("Venue (letters only): ");
            if (venue.matches("^[a-zA-Z\\s]+$")) {
                break;
            } else {
                System.out.println("  Invalid venue. Please use letters and spaces only.");
            }
        }

        Event newEvent = new Event(name, date, types[ti], guests, venue);
        events.add(newEvent);
        activeEvent = newEvent;
        
        System.out.println("\n  Event saved and active: " + name);
    }

    static void browseAndAddDishes() {
        requireEvent();
        if (activeEvent == null) return;

        boolean back = false;
        while (!back) {
            printHeader("Browse Filipino Food Catalog");
            System.out.print("  Press Enter to view catalog (or '0' to go back): ");
            String input = sc.nextLine().trim();
            if (input.equals("0")) {
                back = true;
                continue;
            }

            printDishTable(catalog);
            System.out.println("\n  Enter dish # to toggle add/remove (0 to back)");
            int sel = readInt("Dish number: ", 0, catalog.size());
            if (sel == 0) continue;

            Dish chosen = catalog.get(sel - 1);
            Dish inMenu = findInMenu(chosen.name);
            if (inMenu != null) {
                activeEvent.menu.remove(inMenu);
                System.out.println("  [-] Removed: " + chosen.name);
            } else {
                activeEvent.menu.add(new Dish(chosen.name, chosen.category, chosen.servings));
                System.out.println("  [+] Added: " + chosen.name);
            }
        }
    }

    static void viewMenu() {
        requireEvent();
        if (activeEvent == null) return;
        printHeader(activeEvent.name + " Menu");
        if (activeEvent.menu.isEmpty()) {
            System.out.println("  No dishes added yet.");
            return;
        }
        printMenuTable(activeEvent.menu);
    }

    static void addCustomDish() {
        requireEvent();
        if (activeEvent == null) return;

        printHeader("Add Custom Dish");
        String name = readString("Dish name: ");
        if (findInMenu(name) != null) {
            System.out.println("  Dish already in menu.");
            return;
        }

        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; i++)
            System.out.printf("     %d. %s%n", i+1, cats[i].getLabel());
        int ci = readInt("  Category: ", 1, cats.length) - 1;
        int serves = readInt("Estimated servings: ", 1, 9999);

        activeEvent.menu.add(new Dish(name, cats[ci], serves));
        System.out.println("\n  Added " + name + " to " + activeEvent.name);
    }

    static void printEventSummary() {
        requireEvent();
        if (activeEvent == null) return;

        printHeader("Event Summary");
        System.out.println("  Event  : " + activeEvent.name);
        System.out.println("  Date   : " + activeEvent.formattedDate());
        System.out.println("  Type   : " + activeEvent.type);
        System.out.println("  Guests : " + activeEvent.guestCount);
        System.out.println("  Venue  : " + activeEvent.venue);
        
        if (activeEvent.menu.isEmpty()) {
            System.out.println("\n  No menu items yet.");
        } else {
            printMenuTable(activeEvent.menu);
        }
    }

    static void printHeader(String title) {
        System.out.println("\n== " + title.toUpperCase() + " ==");
    }

    static void printDishTable(List<Dish> dishes) {
        System.out.printf("  %-4s %-30s %-22s%n", "#", "Dish Name", "Category");
        System.out.println("  ----------------------------------------------------------");
        for (int i = 0; i < dishes.size(); i++) {
            Dish d = dishes.get(i);
            System.out.printf("  %-4d %-30s %-22s%n", i+1, d.name, d.category.getLabel());
        }
    }

    static void printMenuTable(List<Dish> items) {
        System.out.printf("\n  %-4s %-28s %-20s %-8s%n", "#", "Dish", "Category", "Serves");
        System.out.println("  ------------------------------------------------------------");
        for (int i = 0; i < items.size(); i++) {
            Dish d = items.get(i);
            System.out.printf("  %-4d %-28s %-20s %-8d%n", i+1, d.name, d.category.getLabel(), d.servings);
        }
    }

    static void requireEvent() {
        if (activeEvent == null) {
            System.out.println("\n  Please create or load an event first.");
            manageEvents();
        }
    }

    static Dish findInMenu(String name) {
        if (activeEvent == null) return null;
        for (Dish d : activeEvent.menu) {
            if (d.name.equalsIgnoreCase(name)) return d;
        }
        return null;
    }

    static String readString(String prompt) {
        System.out.print("  " + prompt);
        return sc.nextLine().trim();
    }

    static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print("  " + prompt);
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("  Please enter between %d and %d.%n", min, max);
            } catch (Exception e) {
                System.out.println("  Invalid input. Enter a number.");
            }
        }
    }
}