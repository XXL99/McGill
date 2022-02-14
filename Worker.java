
public class Worker extends Unit {
	private int numOfJob;

	public Worker(Tile position, double hp,String faction) {
		super(position,hp,2,faction);
		numOfJob=0;
	}
	
	public void takeAction(Tile a) {
		if(!a.isImproved()) {
			a.buildImprovement();
			numOfJob+=1;
			if(numOfJob==10) {
				a.removeUnit(Worker.this);
			}
		}
	}
	public boolean equals(Object e) {
		boolean equalTo=false;
		boolean typeEquals = e instanceof Worker;
		if(typeEquals) {
			boolean factionEquals = this.getFaction().equals(((Worker) e).getFaction());
			boolean positionEquals= this.getPosition().equals(((Worker) e).getPosition());
			boolean hpEquals=this.getHP()==(((Worker)e).getHP());
			boolean numEquals=this.numOfJob==((Worker)e).numOfJob;
			if(factionEquals&&positionEquals&&hpEquals&&numEquals) {	
				equalTo=true;
			}
		}
		return equalTo;
	}

}
