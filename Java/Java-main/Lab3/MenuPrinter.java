package Lab3;

public class MenuPrinter {
    public static void MainMenu(String userName) {
		System.out.print("\033[H\033[2J"); 
		System.out.println("Welcome, " + userName + "!"); 
		System.out.println("0 - Exit");
		System.out.println("1 - Add new item");
		System.out.println("2 - Show items");
		System.out.println("3 - Update DPS");
		System.out.println("4 - Data base");
	}

	public static void AddItemMenu() {
		System.out.print("\033[H\033[2J");  
		System.out.println("0 - Main menu");
		System.out.println("1 - Add new car");
		System.out.println("2 - Add new truck");
	}

	public static void ShowItemsMenu() {
		System.out.print("\033[H\033[2J");  
		System.out.println("0 - Main menu");
		System.out.println("1 - Show cars");
		System.out.println("2 - Show trucks");
	}

	public static void UpdateDPSMenu() {
		System.out.print("\033[H\033[2J");  
		System.out.println("0 - Main menu");
		System.out.println("1 - Update speed limit");
		System.out.println("2 - Update weight limit");
		System.out.println("3 - Update height limit");
	}

	public static void DataBaseMenu() {
		System.out.print("\033[H\033[2J");  
		System.out.println("0 - Main menu");
		System.out.println("1 - Read from data base");
		System.out.println("2 - Write to data base");
		System.out.println("3 - Change item in data bsae");
	}

	public static void ChangeCarMenu() {
		System.out.print("\033[H\033[2J"); 
		System.out.println("0 - Exit");
		System.out.println("1 - Change mark");
		System.out.println("2 - Change max speed");
		System.out.println("3 - Change current station");
		System.out.println("4 - Change status radio");
		System.out.println("5 - Delete car");
	}

	public static void ChangeTruckMenu() {
		System.out.print("\033[H\033[2J"); 
		System.out.println("0 - Exit");
		System.out.println("1 - Change mark");
		System.out.println("2 - Change max speed");
		System.out.println("3 - Change current station");
		System.out.println("4 - Change status radio");
		System.out.println("5 - Change weight");
		System.out.println("6 - Change height");
		System.out.println("7 - Delete truck");
	}
}
