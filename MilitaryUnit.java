
public abstract class MilitaryUnit extends Unit{
	
	private int attackRange;
	private int armor;
	private double attackDam;

	public MilitaryUnit(Tile position,double hp,int moveRange,String faction,double attackDam,int attackRange,int armor) {
		super(position,hp,moveRange, faction);
		this.attackDam=attackDam;
		this.attackRange=attackRange;
		this.armor=armor;
	}
	
	public void takeAction(Tile b) {
		double dist=Tile.getDistance(super.getPosition(), b);
		Unit enemy=b.selectWeakEnemy(super.getFaction());
		if (dist<this.attackRange+1) {
			if(enemy!=null) {
			if(b.isImproved()) {
				attackDam*=1.05;
			}
			enemy.receiveDamage(this.attackDam);
		}
	}
	}
	
	public void receiveDamage(double damReceived) {
		double multipler=100/(100+armor);
			damReceived*=multipler;
		super.receiveDamage(damReceived);
	}
	
}
