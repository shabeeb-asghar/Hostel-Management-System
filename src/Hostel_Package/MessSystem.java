package Hostel_Package;
import java.util.ArrayList;
import java.util.List;

public class MessSystem extends Service {
  private String menu="Menu";
    private List<String> items;

    public MessSystem() {
        super();
        this.items = new ArrayList<>();
    }
    public String getmenu()
    {
        return menu;
    }
    public void addMenu(String item) {
        items.add(item);
    }

    public void updateMenu(String item) {
        // Code to update menu item
    }

    public void removeMenu(String item) {
        items.remove(item);
    }
}
