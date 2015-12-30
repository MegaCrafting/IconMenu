package playerdata;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import menus.IconMenu;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import config.WeaponData;
import Main.DeathMatch;
import Main.GameMode;
import Main.Maps;
import Timers.VoteHandler;
import Timers.VoteTimer;
import Utils.CrackShotAPI;

public class dmPlayer {   //Holds data for players using this plugin


//note removed a bunch of stuff that wasn't relevant to the iconmenus
	private DeathMatch pl = DeathMatch.plugin;
	private UUID uuid = null;
	private IconMenu menu = null;
	
	public dmPlayer(Player p) {
		this.uuid = p.getUniqueId();
		this.plr = p;
		
		if(!DeathMatch.dmPlayers.contains(this))
			DeathMatch.dmPlayers.add(this);
		
		if(!pl.getEcon().hasAccount(p))  //set's up a bank account if the player doesen't have one
			pl.getEcon().createBank("DeathMatch", p);
		pl.getEcon().getBalance(p);
	}

	public void buildBasicGunMenu(Player p) {   //This is the normal gun men (non donor)
		if(this.getMenu() != null)
			this.getMenu().destroy();

		setMenu(new IconMenu("Basic Gun Shop - " + p.getName(), 36, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				WeaponData wep = WeaponData.findWeaponByiName(event.getName());
				if(event.getPosition()==35) {
					event.setWillClose(true);
					return;
				}
				dmPlayer dmp = dmPlayer.finddmPlayer(plr);
				//certian icons on the menu are hard coded..  This could easily be read from a config but I havent
				//gotten that far.    
				
				if(event.getPosition()==33)  //Main Menu Icon
				{
					event.setWillClose(false);
					dmp.buildMainMenu(plr);  //calling the main menu from the gun menu.
					dmp.getMenu().open(plr);
					return;
				}
				if(event.getPosition()==34)  //Donor Menu Icon
				{
					System.out.println("open donor menu");
					event.setWillClose(false);
					dmp.buildDonorGunMenu(plr); 
					dmp.getMenu().open(plr);
					return;
				}
				if(pl.getEcon().getBalance(plr) >= wep.getCost())   //called when a player clicks on a buyable item.
				{
					event.getPlayer().sendMessage("You have purchased " + event.getName() + " for $" + wep.getCost() );
					pl.getEcon().withdrawPlayer(plr, wep.getCost());
					//CrackShotAPI.csu.giveWeapon(event.getPlayer(), wep.getName(), 1);
					IconMenu.formatLore(wep.getName(), event.getPlayer());  //This bit of code formats the lore on a PURCHASED Item
					event.setWillClose(true);
				} else {
					event.getPlayer().sendMessage(ChatColor.RED + "Sorry! You can't currently afford that weapon!");
				}

			}
		}, DeathMatch.plugin));

		int pri = 0;   //Top row = 0-8
		int sec = 9;   //Second row = 9-17
		int hev = 18;  //Third Row = 18-26
		int mel = 27;  //Bottom row = 27-36

		//Load up all primary weapons:
		//here you can load in rows from an array, or list, or config file or whatever.
		
		for(WeaponData w:DeathMatch.loadedWeapons) //loading weapons from a list, list populated at plugin load.
		{
      /* NOTE:   Crackshot's API leaves a lot to be desired, so when deathmatch is loaded I load some additional data for dm
       * This data is stored in WeaponData, both are accessed by the weapon's name since thats how crackshot does it
       */
	 		WeaponData wpn = WeaponData.findWeaponByiName(w.getName());  
			if(wpn.getDonor())
				continue;

			if(wpn.getwClass().equalsIgnoreCase("primary"))  //Load primaries in the first row (0-8)
			{
				String path = w.getName() + ".Item_Information.";
				String[] item_id = csd.getString(path + "Item_Type").split("~"); //CS Uses item id's rather than materials...
				//calls format lore to fix the weapon lore to span multi lines.
				String[] lore = IconMenu.formatLore(csd.getString(path + "Item_Lore"), w.getCost(), wpn.getwClass());  
				
				int item_data = 0;
						if(item_id.length > 1)
							item_data = Integer.parseInt(item_id[1]);
						
				ItemStack is = IconMenu.makeIcon(Integer.parseInt(item_id[0]), 1, (short) item_data);  //creates the icon based on the itemid.
				getMenu().setOption(pri, is, w.getDisplayName(), lore );  //passes the item id to the PLAYERS iconmenu for display
				pri++;  //go to the next icon on the top row

        /* Note 2
         * There is no sanity check to make sure you don't run over 36 items here...  But I was only  going to do 9 of each weapon
         * Max for right now.  You may want to include some sort of sanity checks.
         */

			}
			




		}
    //add in the hardcoded navigation buttons
		getMenu().setOption(35, new ItemStack(Material.BARRIER), "Close", "Close This Menu");
		getMenu().setOption(34, new ItemStack(Material.ENDER_CHEST), "Donor Menu", "Advanced Weapons For Donors");
		getMenu().setOption(33, new ItemStack(Material.BEACON), "Main Menu", "Go Back To The Main Menu");
	}

	public void buildDonorGunMenu(Player p) {  //this one is basically the same as the above menu just for donors items.
		if(getMenu() != null)
			getMenu().destroy();

		setMenu(new IconMenu("Elite Gun Shop - " + p.getName(), 36, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				WeaponData wep = WeaponData.findWeaponByiName(event.getName());
				if(event.getPosition()==35) {
					event.setWillClose(true);
					return;
				}
				dmPlayer dmp = dmPlayer.finddmPlayer(plr);
				if(event.getPosition()==33)
				{
					event.setWillClose(false);
					dmp.buildMainMenu(plr);
					dmp.getMenu().open(plr);
				}
				if(event.getPosition()==34)
				{
					event.setWillClose(false);
					dmp.buildBasicGunMenu(plr);
					dmp.getMenu().open(plr);
					return;
				}				

				
				
				if(wep != null && !plr.hasPermission("DeathMatch.donor"))
				{
					event.getPlayer().sendMessage(ChatColor.RED + "Sorry! items in this shop are reserved for donors.. " + ChatColor.GOLD + " donate.megacrafting.com ");
					return;
				}
				if(wep != null && pl.getEcon().getBalance(plr) >= wep.getCost())
				{
					event.getPlayer().sendMessage("You have purchased " + event.getName() + " for $" + wep.getCost() );
					pl.getEcon().withdrawPlayer(plr, wep.getCost());
					CrackShotAPI.csu.giveWeapon(event.getPlayer(), wep.getName(), 1);
					IconMenu.formatLore(wep.getName(), event.getPlayer());
					event.setWillClose(true);
				} else {
					event.getPlayer().sendMessage(ChatColor.RED + "Sorry! You can't currently afford that weapon!");
				}

			}
		}, DeathMatch.plugin));

		int pri = 0;
		int sec = 9;
		int hev = 18;
		int mel = 27;

		//Load up all primary weapons:
		CSDirector csd = CrackShotAPI.csd;
		for(WeaponData w:DeathMatch.loadedWeapons)
		{

			WeaponData wpn = WeaponData.findWeaponByiName(w.getName());
			if(!wpn.getDonor())
				continue;

			if(wpn.getwClass().equalsIgnoreCase("primary"))
			{
				String path = w.getName() + ".Item_Information.";
				String[] item_id = csd.getString(path + "Item_Type").split("~");
				String[] lore = IconMenu.formatLore(csd.getString(path + "Item_Lore"), w.getCost(), wpn.getwClass());
				
				int item_data = 0;
				if(item_data > 1)
				 item_data = Integer.parseInt(item_id[1]);
				ItemStack is = IconMenu.makeIcon(Integer.parseInt(item_id[0]), 1, (short) item_data);
				getMenu().setOption(pri, is, w.getDisplayName(), lore );
				pri++;


			}
			if(wpn.getwClass().equalsIgnoreCase("secondary"))
			{
				String path = w.getName() + ".Item_Information.";
				String[] item_id = csd.getString(path + "Item_Type").split("~");
				String[] lore = IconMenu.formatLore(csd.getString(path + "Item_Lore"), w.getCost(), wpn.getwClass());
				
				int item_data = 0;
				if(item_data > 1)
				 item_data = Integer.parseInt(item_id[1]);
				ItemStack is = IconMenu.makeIcon(Integer.parseInt(item_id[0]), 1, (short) item_data);
				getMenu().setOption(sec, is, w.getDisplayName(), lore );
				sec++;

			}
			if(wpn.getwClass().equalsIgnoreCase("heavy"))
			{
				String path = w.getName() + ".Item_Information.";
				String[] item_id = csd.getString(path + "Item_Type").split("~");
				String[] lore = IconMenu.formatLore(csd.getString(path + "Item_Lore"), w.getCost(), wpn.getwClass());
				
				int item_data = 0;
				if(item_data > 1)
				 item_data = Integer.parseInt(item_id[1]);
				ItemStack is = IconMenu.makeIcon(Integer.parseInt(item_id[0]), 1, (short) item_data);
				getMenu().setOption(hev, is, w.getDisplayName(), lore );
				hev++;

			}
			if(wpn.getwClass().equalsIgnoreCase("melee"))
			{
				String path = w.getName() + ".Item_Information.";
				String[] item_id = csd.getString(path + "Item_Type").split("~");
				String[] lore = IconMenu.formatLore(csd.getString(path + "Item_Lore"), w.getCost(), wpn.getwClass());
				
				int item_data = 0;
				if(item_data > 1)
				 item_data = Integer.parseInt(item_id[1]);
				ItemStack is = IconMenu.makeIcon(Integer.parseInt(item_id[0]), 1, (short) item_data);
				getMenu().setOption(mel, is, w.getDisplayName(), lore );
				mel++;

			}





		}

		getMenu().setOption(35, new ItemStack(Material.BARRIER), "Close", "Close This Menu");
		getMenu().setOption(34, new ItemStack(Material.CHEST), "Gun Shop", "Open The Basic Gun Shop Menu");
		getMenu().setOption(33, new ItemStack(Material.BEACON), "Main Menu", "Go Back To The Main Menu");


	}
	public void buildMainMenu(Player p) {   //Create the main menu accessed when someone does /vote.
		if(getMenu() != null)
			getMenu().destroy();

		setMenu(new IconMenu("Main Menu - " + p.getName(), 36, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {

				dmPlayer dmp = dmPlayer.finddmPlayer(event.getPlayer());
				if(event.getPosition()==27)  //Again these are hard coded and could be done via a config if so desired.
				{
					dmp.buildBasicGunMenu(event.getPlayer());
					dmp.getMenu().open(event.getPlayer());
					event.setWillClose(false);

				}
				if(event.getPosition()==28)
				{
					dmp.buildDonorGunMenu(event.getPlayer());
					dmp.getMenu().open(event.getPlayer());
					event.setWillClose(false);

				}
				if(event.getPosition()==32 || event.getPosition() == 33 || event.getPosition() == 30)
				{
					if(VoteTimer.isHasStarted())
					{
						if(event.getPosition() == 32) //voting yes
						{
							VoteTimer.voteYes(event.getPlayer());
							VoteTimer.getHasVoted().add(event.getPlayer());
						} else if(event.getPosition() == 33) {
							VoteTimer.voteNo(event.getPlayer());
							VoteTimer.getHasVoted().add(event.getPlayer());
						} 

					} else {
						event.getPlayer().sendMessage(ChatColor.RED + "Sorry!  There isn't currently anything to vote on!");
					}

				}
				if(event.getPosition() == 30) {
					if(DeathMatch.mode != 0) {
						event.getPlayer().sendMessage(ChatColor.RED + "Sorry, this round is already in process, voting to start it is silly.");
						return;
					}
					VoteHandler.setStart(event.getPlayer());
					return;
				}

				if(event.getPosition()==35)
				{
					event.setWillClose(true);
					return;
				}
				if(event.getPosition()==29)
				{
					dmp.buildAmmoMenu(event.getPlayer());
					dmp.getMenu().open(event.getPlayer());
					event.setWillClose(false);
					
					return;
				}


				if(!VoteTimer.isHasStarted()) {
					if(event.getPosition() <= 8) {
						GameMode mode = GameMode.findGameMode(event.getName());
						if(mode != null)
							VoteHandler.SetMode(event.getPlayer(), mode);
					}
					if(event.getPosition() >= 9 && event.getPosition() <= 26)
					{
						Maps map = GameMode.findMap(event.getName());
						if(map != null)
							VoteHandler.setMap(event.getPlayer(), map);
					}
				} else if (event.getPosition() >= 27){
					event.getPlayer().sendMessage(ChatColor.RED + "SORRY! there is already a vote in progress!");

				}



			}
		}, DeathMatch.plugin));

		int pri = 0;
		int sec = 9;
		List<Material> icons = new ArrayList<Material>();

		icons.add(Material.IRON_AXE);
		icons.add(Material.GOLD_AXE);
		icons.add(Material.DIAMOND_AXE);

		icons.add(Material.IRON_SWORD);
		icons.add(Material.GOLD_SWORD);
		icons.add(Material.DIAMOND_SWORD);
		for(int i = 0; i < DeathMatch.loadedModes.size(); i++)
		{
			GameMode mode = DeathMatch.loadedModes.get(i);
			String[] desc = Utils.tools.formatDesc(mode.getDescription());

			ItemStack is = new ItemStack(icons.get(i));
			ItemMeta im = is.getItemMeta();
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			im.addItemFlags(ItemFlag.HIDE_DESTROYS);

			is.setItemMeta(im);


			getMenu().setOption(pri, is, mode.getTag(), desc);

			pri ++;
		}
		for(int i = 0; i < DeathMatch.loadedMaps.size(); i++)
		{
			Maps map = DeathMatch.loadedMaps.get(i);

			String[] desc = Utils.tools.formatDesc(map.getDesc());

			ItemStack is = new ItemStack(Material.EMPTY_MAP);
			ItemMeta im = is.getItemMeta();
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			is.setItemMeta(im);
			getMenu().setOption(sec, is, map.getName(), desc);
			sec ++;
		}

		ItemStack is = new ItemStack(Material.CHEST);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is.setItemMeta(im);
		getMenu().setOption(27, is, "Gun Shop", "Open The Basic Gun Shop Menu");

		ItemStack is2 = new ItemStack(Material.ENDER_CHEST);
		ItemMeta im2 = is2.getItemMeta();
		im2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is2.setItemMeta(im2);
		getMenu().setOption(28, is2, "Donor Shop", "Open The Advanced Weapon Menu");

		ItemStack is6 = new ItemStack(Material.GHAST_TEAR);
		
		ItemMeta im6 = is6.getItemMeta();
		is6.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
		im6.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im6.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		getMenu().setOption(29, is6, "Ammo Shop", "Open The Ammo Menu");
		
		
		ItemStack is3 = new ItemStack(Material.WOOL);
		ItemMeta im3 = is3.getItemMeta();
		im3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is3.setItemMeta(im3);
		is3.setDurability((short) 5);
		getMenu().setOption(32, is3, "Vote Yes", "Vote yes on a pending vote");

		ItemStack is4 = new ItemStack(Material.WOOL);
		ItemMeta im4 = is4.getItemMeta();
		im4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is4.setItemMeta(im4);
		is4.setDurability((short) 14);
		getMenu().setOption(33, is4, "Vote No", "Vote no on a pending vote");

		ItemStack is5 = new ItemStack(Material.BARRIER);
		ItemMeta im5 = is5.getItemMeta();
		im5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is5.setItemMeta(im5);
		getMenu().setOption(35, is5, "Close", "Close This Menu");

		getMenu().setOption(30, new ItemStack(Material.EYE_OF_ENDER), "Start Game", "Click here to start the next round!");
		//Load up all primary weapons:
	}

	protected void buildAmmoMenu(Player p) {   //we only have 3 types of ammo for every gun, so this is pretty simple
		if(getMenu() != null)
			getMenu().destroy();

		setMenu(new IconMenu("Ammo Shop - " + p.getName(), 36, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				
				int cost = -1;
				int amt = 0;
				if(event.getPosition()==0) { // Primary Ammo
					cost = 150;
					amt = 64;
				}
				if(event.getPosition() == 9) { // secondary ammo
					cost = 500;
					amt = 32;
				}
				if(event.getPosition() == 18) { // heavy ammo
					cost = 5000;
					amt = 6;
				}
				dmPlayer dmp = dmPlayer.finddmPlayer(plr);			
				if(cost >= 0 && pl.getEcon().getBalance(plr) >= cost)
				{
					PlayerInventory pi = plr.getInventory();
					if(pi.firstEmpty() == -1)
					{
						plr.sendMessage(ChatColor.RED + "Sorry!  You don't have any empty inventory slots!");
						return;
					}
					event.getPlayer().sendMessage("You have purchased " + event.getName() + " for $" + cost );
					pl.getEcon().withdrawPlayer(plr, cost);
					int pos = event.getPosition();
					ItemStack ammo = dmp.menu.getIcon(pos);
					ammo.setAmount(amt);
					
					ammo = CrackShotAPI.makeAmmoBrick(ammo);
					 
					pi.addItem(ammo);
					event.setWillClose(true);
				} else {
					event.getPlayer().sendMessage(ChatColor.RED + "Sorry! You can't currently afford that!");
					return;
				}

				if(event.getPosition()==35) {
					event.setWillClose(true);
					return;
				}
				
				if(event.getPosition()==33)
				{
					event.setWillClose(false);
					dmp.buildMainMenu(plr);
					dmp.getMenu().open(plr);
				}
				if(event.getPosition()==34)
				{
					event.setWillClose(false);
					dmp.buildBasicGunMenu(plr);
					dmp.getMenu().open(plr);
					return;
				}				

				

			}
		}, DeathMatch.plugin));

		int pri = 0;
		int sec = 9;
		int hev = 18;
		int mel = 27;

		//Load up all primary weapons:
		
		ItemStack isprimary = new ItemStack(Material.GHAST_TEAR);
		ItemStack issecondary = new ItemStack(Material.BLAZE_POWDER);
		ItemStack isheavy = new ItemStack(Material.NETHER_STAR);
		String[] lore = new String[4];
		isprimary = CrackShotAPI.makeAmmoBrick(isprimary);
		
		lore[0] =  "A box containing ammo for ";
		lore[1] = ChatColor.GREEN + "Primary Weapons";
		lore[2] ="(Pistols, Submachineguns Etc.)";
		lore[3] = ChatColor.YELLOW + "Cost: $150 / 64 Rounds";
		isprimary.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
		issecondary.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
		getMenu().setOption(0, isprimary, isprimary.getItemMeta().getDisplayName(), lore);
		
		lore[1] =  ChatColor.RED + "Secondary Weapons";
		lore[2] ="(Shotguns, Snipers Etc.)";
		lore[3] = ChatColor.YELLOW + "Cost: $500 / 32 Rounds";
		issecondary = CrackShotAPI.makeAmmoBrick(issecondary);
		getMenu().setOption(9, issecondary, issecondary.getItemMeta().getDisplayName(), lore);
		
		lore[1] = ChatColor.BLUE + "Heavy Weapons";
		lore[2] ="(Rocket Launchers, Mini Gun, Etc.)";
		lore[3] = ChatColor.YELLOW + "Cost: $5000 / 6 Rounds";
		isheavy = CrackShotAPI.makeAmmoBrick(isheavy);
		getMenu().setOption(18, isheavy, isheavy.getItemMeta().getDisplayName(), lore);
		ItemStack is = new ItemStack(Material.CHEST);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is.setItemMeta(im);
		getMenu().setOption(27, is, "Gun Shop", "Open The Basic Gun Shop Menu");

		ItemStack is2 = new ItemStack(Material.ENDER_CHEST);
		ItemMeta im2 = is2.getItemMeta();
		im2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is2.setItemMeta(im2);
		getMenu().setOption(28, is2, "Donor Shop", "Open The Advanced Weapon Menu");

		ItemStack is5 = new ItemStack(Material.BARRIER);
		ItemMeta im5 = is5.getItemMeta();
		im5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is5.setItemMeta(im5);
		getMenu().setOption(35, is5, "Close", "Close This Menu");

		getMenu().setOption(30, new ItemStack(Material.EYE_OF_ENDER), "Start Game", "Click here to start the next round!");	
			
			

		
		
	}

/* This bit of code finds a given dmPlayer and returns it, or creates a new one if need be.   
 * be sure to create one on join and remove it when the player leaves as they're accessed by UUID
 * multiple entries in the dmPlayers List may give you screwy results if you don't do cleanup!
 */
	public static dmPlayer finddmPlayer(Player p) { 
	
		
		for(int i = 0 ; i < DeathMatch.dmPlayers.size() ; i++)  //search all the active dmPlayers for a match first.
		{
			dmPlayer dmp = DeathMatch.dmPlayers.get(i);
			if(p.getUniqueId() == dmp.getUUID()) 
				return dmp;
			
				
		}
		dmPlayer dmp = new dmPlayer(p);  //None found so lets create one!
		System.out.println("Creating new dmPlayer for " + dmp.getPlr().getName() + " - " + dmp.getUUID() + " dmp Size " + DeathMatch.dmPlayers.size()) ;
		dmp.setPlr(p);   //set their player (i think this is already done in new dmPlayer())
		return dmp;
	}

	private UUID getUUID() {
		return uuid;
	}

	public boolean isHasVoted() {
		return hasVoted;
	}

	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public Player getLastKilled() {
		return lastKilled;
	}

	public void setLastKilled(Player lastKilled) {
		this.lastKilled = lastKilled;
	}

	public Player getLastKiller() {
		return lastKiller;
	}

	public void setLastKiller(Player lastKiller) {
		this.lastKiller = lastKiller;
	}

	public Player getPlr() {
		return plr;
	}

	public void setPlr(Player plr) {
		this.plr = plr;
	}

	public int getzKills() {
		return zKills;
	}

	public void setwolfKills(int zKills) {
		this.wKills++;
	}

	public void setzombieKills(int zKills) {
		this.zKills++;
	}

	public int getcKills() {
		return cKills;
	}

	public void setcreeperKills(int cKills) {
		this.cKills++;
	}

	public int getsKills() {
		return sKills;
	}

	public void setskeletonKills(int sKills) {
		this.sKills++;
	}

	public int getbKills() {
		return bKills;
	}

	public void setblazeKills(int bKills) {
		this.bKills++;
	}

	public int getpKills() {
		return pKills;
	}

	public void setplayerKills(int pKills) {
		this.pKills++;
	}

	public int getslimeKills() {
		return slKills;
	}

	public void setslimeKills(int slKills) {
		this.slKills++;
	}
	public int getpigKills() {
		return pigKills;
	}

	public void setpigKills(int i) {
		this.pigKills++;
	}

	public int getwolfKills() {
		return wKills;
	}


	public ScoreBoard getScoreb() {
		return scoreb;
	}

	public void setScoreb(ScoreBoard scoreBoard) {
		this.scoreb = scoreBoard;
	}

	public int getCashEarned() {
		return cashEarned;
	}

	public void setCashEarned(int cashEarned) {
		this.cashEarned = cashEarned;
	}

	public int getHighScore() {
		
		return getPkills() + zKills + cKills+sKills+bKills+slKills+pKills+spKills+wKills+pigKills;
		
	}
	public double getKDR() {
		if(deaths > 0)
			return getPkills() / deaths;
		else
			return pKills / 1;
	}

	public void clearScores() {
		this.setPkills(0);
		this.zKills =0;
		this.cKills =0;
		this.sKills =0;
		this.bKills =0;
		this.slKills =0;
		this.pKills =0;
		this.spKills =0;
		this.wKills =0;
		this.pigKills =0;
		
	}

	public int getzKillsT() {
		return zKillsT;
	}

	public void setzKillsT(int zKillsT) {
		this.zKillsT = zKillsT;
	}

	public int getcKillsT() {
		return cKillsT;
	}

	public void setcKillsT(int cKillsT) {
		this.cKillsT = cKillsT;
	}

	public void setsKillsT(int sKillsT) {
		this.sKillsT = sKillsT;
		
	}

	public void setslKillsT(int int1) {
		this.slKillsT = int1;
		
	}

	public void setwKillsT(int int1) {
		this.setwKillsT(int1);
		
	}

	public void setpigKillsT(int int1) {
		this.setpigKillsT(int1);
		
	}

	public void setbKillsT(int int1) {
		this.setbKillsT(int1);
		
	}

	public void setpKillsT(int int1) {
		this.setpKillsT(int1);
		
	}

	public int getDeathsT() {
		return deathsT;
	}

	public void setDeathsT(int deathsT) {
		this.deathsT = deathsT;
	}

	public int getsKillsT() {

		return sKillsT;
	}

	public int getslKillsT() {
		
		return slKillsT;
	}

	public int getwKillsT() {
		// TODO Auto-generated method stub
		return wKillsT;
	}

	public int getpigKillsT() {
		// TODO Auto-generated method stub
		return pigKillsT;
	}

	public int getbKillsT() {
		// TODO Auto-generated method stub
		return bKillsT;
	}

	public int getpKillsT() {
		// TODO Auto-generated method stub
		return pKillsT;
	}

	public IconMenu getMenu() {
		return menu;
	}

	public void setMenu(IconMenu menu) {
		this.menu = menu;
	}

	public int getRespawnTask() {
		return respawnTask;
	}

	public void setRespawnTask(int respawnTask) {
		this.respawnTask = respawnTask;
	}

	public List<ItemStack> getSavedItems() {
		return savedItems;
	}

	public void setSavedItems(List<ItemStack> savedItems) {
		this.savedItems = savedItems;
	}

	public Leaderboard getLeaderboard() {
		return leaderboard;
	}

	public void setLeaderboard(Leaderboard leaderboard) {
		this.leaderboard = leaderboard;
	}

	public int getPkills() {
		return pkills;
	}

	public void setPkills(int pkills) {
		this.pkills = pkills;
	}

}
