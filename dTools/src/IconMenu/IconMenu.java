package menus;

import java.util.ArrayList;
import java.util.Arrays;
 
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import config.WeaponData;
import Utils.CrackShotAPI;
 
public class IconMenu implements Listener {
 
    private String name;
    private int size;
    private OptionClickEventHandler handler;
    private Plugin plugin;
   
    private String[] optionNames;
    private ItemStack[] optionIcons;
   
    public IconMenu(String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        this.name = name;
    	if(this.name.length() > 32) {
    		System.out.println("[Death Match]: Shortened icon menu name to 32 chars.. "+ this.name);   //make sure your inventory names arent over 32 chars
    		this.name = this.name.substring(0,31);
    	}
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }
    public ItemStack getIcon(int position)
    {
    	return this.optionIcons[position];
    }
/*    
    public IconMenu setOption(int position, ItemStack icon, String name, String info) {
        optionNames[position] = name;
        ItemMeta im = icon.getItemMeta();
        List<String> lore = im.getLore();
        lore.add(info);
        optionIcons[position] = setItemNameAndLore(icon, name, lore);
        return this;
    }
    */
    public void open(Player player) {

        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }
   
    public void destroy() {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }
   
    @EventHandler(priority=EventPriority.MONITOR)
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                Plugin plugin = this.plugin;
                OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, optionNames[slot]);
                handler.onOptionClick(e);
                if (e.willClose()) {
                    final Player p = (Player)event.getWhoClicked();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            p.closeInventory();
                        }
                    }, 1);
                }
                if (e.willDestroy()) {
                    destroy();
                }
            }
        }
    }
   
    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);      
    }
   
    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
       
        public OptionClickEvent(Player player, int position, String name) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
        }
       
        public Player getPlayer() {
            return player;
        }
       
        public int getPosition() {
            return position;
        }
       
        public String getName() {
            return name;
        }
       
        public boolean willClose() {
            return close;
        }
       
        public boolean willDestroy() {
            return destroy;
        }
       
        public void setWillClose(boolean close) {
            this.close = close;
        }
       
        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
   
    private ItemStack setItemNameAndLore(ItemStack item, String name, String... info) {
        ItemMeta im = item.getItemMeta();
            im.setDisplayName(name);
            im.setLore(Arrays.asList(info));
        item.setItemMeta(im);
        return item;
    }
	public static ItemStack makeIcon(int id, int stack_size, short item_data) {   //creates an item stack with all the meta bullshit stripped off
		ItemStack is = new ItemStack(id, stack_size, (short) item_data );
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_DESTROYS);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		is.setItemMeta(im);
		return is;
	}
	
	public static String[] formatLore(String item_info, int cost, String wClass) {  // This just adds a cost to the existing lore for the GUI only
		item_info = ChatColor.GOLD + wClass + " Weapon|" + ChatColor.YELLOW +  item_info + "|Cost: " + cost;
		String[] lore = item_info.split("\\|");
		return lore;
	}
	
	public static void formatLore(String weaponName, Player p) {   // Pulls weapon lore from crackshot config.
		String path = weaponName + ".Item_Information.";
		String item_info = CrackShotAPI.csd.getString(path + "Item_Lore");
		WeaponData wpn = WeaponData.findWeapon(weaponName);
		String[] info = IconMenu.formatLore(item_info, wpn.getCost(), wpn.getwClass());
		List<String> lore = new ArrayList<String>();
		
		for(int i = 0; i < info.length; i++)
		{
			lore.add(info[i]);
		}
		
	
		
		
		for(ItemStack is:p.getInventory())
		{
			if(is != null)
			{
				ItemMeta im = is.getItemMeta();
				if(im.getDisplayName() != null && im.getDisplayName().contains(weaponName))
				{
					if(wpn.getDonor())
						is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					
					im.setLore(lore);
					is.setItemMeta(im);
					
					
				}
			}
		}
	}
    
   
}
