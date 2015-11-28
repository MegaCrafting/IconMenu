		//In Plugin main loop/declarations
		
			public static List<String> announcements = new ArrayList<String>();
	    public static Map<Player, Integer> announceCount = new HashMap<Player, Integer>();
	    
	 //In wherever
		announcements.add("&aMEGA SATURDAY SALE - 30%OFF EVERYTHING");
		announcements.add("&bMEGA SATURDAY SALE - 30%OFF EVERYTHING");
		announcements.add("&eMEGA SATURDAY SALE - 30%OFF EVERYTHING");
		announcements.add("&aMEGA SATURDAY SALE - 30%OFF EVERYTHING");
		announcements.add("&bMEGA SATURDAY SALE - 30%OFF EVERYTHING");
		announcements.add("&eMEGA SATURDAY SALE - 30%OFF EVERYTHING");
		
		
//In OnEnable()
startBartimer();


//Routine to add players and display msg
	public void startBartimer()
	{
		this.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
		{
			public void run()
			{
				for(Player player : dTools.plugin.getServer().getOnlinePlayers())
				{

					if(announceCount.containsKey(player))
					{
						if(BarAPI.hasBar(player))
							BarAPI.removeBar(player);
						String msg = colorize(announcements.get(announceCount.get(player)));
						
						BarAPI.setMessage(player, msg);
						int acount = announceCount.get(player);
						acount ++;
						if(acount >= announcements.size())
							acount = 0;
						announceCount.remove(player);
						announceCount.put(player, acount);
						
					} else {
						announceCount.put(player, 0);
					}
					
					


				}
			}
		}
		, 6, 6);
	}
	
	
	/*
	* Suggestions:
	* Remove players from announceCount upon quit.
	*/
