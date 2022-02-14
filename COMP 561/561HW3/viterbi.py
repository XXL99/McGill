from Bio import SeqIO

#import of gene in fasta_file
fasta_file="/Users/xinxinlu/Desktop/Vibrio_vulnificus.ASM74310v1.dna.toplevel.fa" #observation
id_list=[]
obs_list=[]
for seq_record in SeqIO.parse(fasta_file, "fasta"):
    id_list.append(seq_record.id)
    obs_list.append(seq_record.seq)
#for each element in the obs_list, there is a squence

#parameter setting, use dictionary to present
states=['i','s','m','e']#i: intergene; s:start; m:middle; e:end
transitions={('i','s'):0.0012610340479192938, #from i to s
             ('i','i'):0.9987389659520807,
             ('i','e'):0,
             ('s','m'):1,
             ('s','s'):0,
             ('s','e'):0,
             ('s','i'):0,
             ('m','s'):0,
             ('m','e'):0.0030303030303030303,
             ('m','m'):0.996969696969697,
             ('m','i'):0,
             ('m','s'):0,
             ('e','i'):1,
             ('e','e'):0,
             ('e','s'):0,
             ('e','m'):0,
             ('i','m'):0}
emissions={'i':{'A': 0.266,'C': 0.243,'G':0.226,'T':0.265},
           's':{'A': 0.016667,'C': 0,'G': 0.016667,'T':0.016667},
           'm':{'A': 0.2,'C': 0.3,'G':0.3,'T':0.2},
           'e':{'A': 0.066667,'C': 0,'G':0.033333,'T':0.05}}

pi=[1.0,0,0,0]

def getMax(values):
    maxV = values[0]
    maxI = 0
    for ind, val in enumerate(values):
        if val>maxV:
            maxV = val
            maxI = ind
    return maxV, maxI

def initialize(row,col,x=0):
    M = []
    for i in range(0,row):
        M.append([])
        for j in range(0,col):
            M[i].append(x)
    return M

def viterbi(states,transitions,emissions,sequence):
    Vp = initialize(len(states),len(sequence))
    V = initialize(len(states),len(sequence),states[0])

    for i in range(0,len(states)-1):
        Vp[i][0] =pi[i]*emissions[states[i]][sequence[0]]
    for j in range(1,len(sequence)):
        for i in range(len(states)-1):
            cur = []
            for k in range(0,len(states)-1): #k is the last state to i 
                cur.append(Vp[k][j-1]*transitions[(states[k],states[i])]*emissions[states[i]][sequence[j-1]])
            maxVal, maxInd = getMax(cur)
            Vp[i][j] = maxVal
            V[i][j] = states[maxInd]   
            
    Vlast=V[len(states)-1][len(sequence)-1]#traceback from the last element
    path=[Vlast]
    
    for j in range(len(sequence)-1,0,-1):
        maxF=-1
        im=0
        jm=0
        for i in range(len(states)-1,0,-1):
           if Vp[i][j]>maxF:
               maxF=Vp[i][j]
               im=i
               jm=j
           else:
                continue
        path.append(V[im][jm])
    return path[::-1]

 
def annotation(path,id_list):
   st=[]
   sp=[]
   sentence=[]
   for i in range(len(path)-1):
       if(path[i]=='s'):
         st.append(i)
       elif(path[i]=='e'):
         sp.append(path.index(i))
       else:
            continue
   if (len(sp)<len(st)):
       for i in range(len(st)-len(sp)-1):
           sp.append(-1)
   for j in range(0,len(st)-1):
        s=(id_list[j]+" ena"+" CDS "+ str(st[j])+" "+str(sp[j])+" ."+" +"+" 0"+" .")
        sentence.append(s)
   return sentence

f=open("Q1c.gff3","w")

for i in range(len(obs_list)):
   path=viterbi(states,transitions,emissions,obs_list[i])
   
   f.writelines("\n".join(annotation(path,id_list)))
f.close()


                 