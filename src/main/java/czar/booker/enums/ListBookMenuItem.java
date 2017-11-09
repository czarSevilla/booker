package czar.booker.enums;

public enum ListBookMenuItem {
	NEWS(1),
	LAST_VIEWS(2),
	FAVORITES(3),
	ALL(4),
	DEPRECATED(5);
	
	private int id;
	
	private ListBookMenuItem(int pId) {
		this.id = pId;
	}
	
	public static ListBookMenuItem findById(int pId) {
		for (ListBookMenuItem item : values()) {
			if (item.id == pId) {
				return item;
			}
		}
		return null;
	}
	
	public int getId() {
		return this.id;
	}
}
