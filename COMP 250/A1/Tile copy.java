
public class Tile {
	private int xCoordinate;
	private int yCoordinate;
	boolean cityOnTile;
	boolean tileImproved;
	ListOfUnits allUnits=new ListOfUnits();
	
	public Tile(int x,int y) {
		x=xCoordinate;
		y=yCoordinate;
		cityOnTile=false;
		tileImproved=false;
	}
	
	public int getX() {
		return xCoordinate;
	}
	
	public int getY() {
		return yCoordinate;
	}
	
	public boolean isCity() {
		return cityOnTile;
	}
	public boolean isImproved() {
		return tileImproved;
	}
	public void foundCity() {
		if(isCity()==false) {
			cityOnTile=true;
		}
	}
	public void buildImprovement() {
		if(isImproved()==false) {
			tileImproved=true;
		}
	}
	public boolean addUnit(Unit newOne) {
		boolean addSucceed=false;
		MilitaryUnit[] ourArmy=allUnits.getArmy();
		if(newOne instanceof MilitaryUnit) {
			if(ourArmy.length==0) {
				allUnits.add(newOne);
				addSucceed=true;
			}else {
				for(int i=0;i<ourArmy.length;i++) {
					if(newOne.getFaction().equals((allUnits.getArmy())[i].getFaction())) {
						allUnits.add(newOne);
						addSucceed=true;
						break;
					}else {				
						continue;
					}
				}
			}
		}else {
		allUnits.add(newOne);
		addSucceed=true;
		}
		return addSucceed;
	}
	public boolean removeUnit(Unit oldOne) {
		boolean removeSucceed=false;
		removeSucceed=allUnits.remove(oldOne);
		return removeSucceed;
	}
	
	public Unit selectWeakEnemy(String faction) {
		Unit enemy=null;
		double lowestHp=Double.MAX_VALUE;
		for(int i=0;i<allUnits.size();i++) {
			if(!allUnits.get(i).getFaction().equals(faction)) {
				if(allUnits.get(i).getHP()<lowestHp) {
					lowestHp=allUnits.get(i).getHP();
					enemy=allUnits.get(i);
				}
			}
		}
		return enemy;
	}
	
	public static double getDistance(Tile a,Tile b) {
		int x1=a.getX();
		int y1=a.getY();
		int x2=b.getX();
		int y2=b.getY();
		
		double distance=Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2), 2));
		return distance;
	}
	
}
