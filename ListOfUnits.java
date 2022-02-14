
public class ListOfUnits {
	private int size;
	private Unit[] listOfUnit;

	public ListOfUnits() {
		this.listOfUnit=new Unit[10];
	}
	
	public int size() {
		 this.size=0;
		for(int i=0;i<this.listOfUnit.length;i++){
			if(this.listOfUnit[i]!=null) {
				this.size++;	
			}else {
				continue;
			}
		}
		return this.size;
	}
	
	public Unit[] getUnits() {
		Unit[] allUnits=new Unit[this.size()];
		for(int i=0,j=0;i<this.listOfUnit.length;i++){
			if(this.listOfUnit[i]!=null) {
				allUnits[j]=this.listOfUnit[i];
				j++;
			}else {
				continue;
			}
		}
		return allUnits;
	}
	
	public Unit get(int local){
			Unit x= this.listOfUnit[local];
			return x;
		}
	
	public void add(Unit a) {
			if (this.size==this.listOfUnit.length) {
				int old=this.listOfUnit.length;
				int newSize=old+old/2+1;
				Unit[] tempArr=new Unit[newSize];
				Unit[] arrList=this.getUnits();
				for(int i=0;i<this.size;i++) {
					tempArr[i]=arrList[i];
				}
				tempArr[this.size]=a;
				this.listOfUnit=tempArr;
				this.size++;
			}else {
				listOfUnit[this.size]=a;
				this.size++;
				
				}
			}

	public int indexOf(Unit firstOccur) {
		int position=0;
		for(int i=0;i<this.size();i++) {
			if(firstOccur.equals(listOfUnit[i])) {
				position=i;
				break;
			}else {
				position=-1;
			}
		}
		return position;
	}
	
	public boolean remove(Unit removed) {
		boolean d;
		int r=indexOf(removed);
		if(r==-1) {
			d=false;	
		}else {
				if(r==this.size-1) {
						listOfUnit[r]=null;
				}else {
					for(int j=r;j<this.size-1;j++) {
						listOfUnit[j]=listOfUnit[j+1];
						}
						listOfUnit[this.size-1]=null;
				}
			d=true;
		}
			return d;
	}
	
	public MilitaryUnit[] getArmy() {
		MilitaryUnit[] army=new MilitaryUnit[this.sizeArmy()];
		for(int i=0,j=0;i<this.listOfUnit.length;i++) {
			if(this.listOfUnit[i] instanceof MilitaryUnit && this.listOfUnit[i]!=null) {
				if(j<sizeArmy()) {
				army[j]=(MilitaryUnit) listOfUnit[i];
				j++;
				}
			}
		}
		return army;
	}
	
	private int sizeArmy() {
		int sizeArmy=0;
		for(int i=0;i<this.listOfUnit.length;i++) {
			if(this.listOfUnit[i] instanceof MilitaryUnit && this.listOfUnit[i]!=null) {
				sizeArmy++;
			}
		}return sizeArmy;
	}
}

