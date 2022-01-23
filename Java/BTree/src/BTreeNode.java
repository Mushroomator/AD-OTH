import java.util.ArrayList;

public class BTreeNode<T extends Comparable<T>> {
    private ArrayList<BTreeEntry<T>> entries;
    private ArrayList<BTreeNode<T>> nextNodes;
    private final int order;

    public BTreeNode(int order) {
        // in this implementation: allocate max. required memory immediately
        // assumption: memory is not critical here, performance is, so prevent resizing of array
        this.entries = new ArrayList<>(order - 1);
        this.nextNodes = new ArrayList<>(order);
        this.order = order;
    }

    /**
     *
     * @param newEntry MUST NOT be null
     * @return
     */
    public void insert(BTreeEntry<T> newEntry){
        int i = 0;
        if(nextNodes.size() == 0){
            // we are within a leaf
            for (i = 0; i < entries.size(); i++) {
                if(entries.get(i).compareTo(newEntry) > 0){
                    // spot where element needs to be inserted was found (= i)
                    if(entries.size() <= order - 2){
                        // there is still place within the node
                        for(int j = entries.size() - 1; j >= i; j--) entries.set(j, entries.get(j-1));
                        entries.set(i, newEntry);
                        break;
                    } else {
                        // no place within the leaf node -> split node
                        System.out.printf("Node %s has no place left. Splitting node...\n", this.entries);
                        //split(this, i);
                    }
                };
            }
        }
        else {
            // we are within the root or an inner node
            for (int j = 0; j < entries.size(); j++) {
                // when the value in this node is bigger than the
                if(entries.get(j).compareTo(newEntry) > 0){
                    nextNodes.get(j).insert(newEntry);
                    return;
                }
            }
            // key to be inserted is bigger than all values in this node
            // last child has to do the insertion
            nextNodes.get(entries.size()).insert(newEntry);
        }
        //
        if (entries.size() == 0) entries.add(newEntry);
        else {
            for(int j = entries.size() - 1; j > i; j--) entries.set(j, entries.get(j-1));
            entries.set(i, newEntry);
        }

    }

    public void split(BTreeNode<T> node){

    }

    public void printRoot(int spacesBetweenNodes){
        var stringsAllHeights = new ArrayList<StringBuilder>();
        this.print(0, stringsAllHeights, spacesBetweenNodes);

        // get StringLength of last level to get middle
        var lastLevel = stringsAllHeights.get(stringsAllHeights.size() - 1);
        lastLevel.setLength(lastLevel.length() - 2);
        var middle = lastLevel.length();

        // loop through all levels starting at root
        // - compute level specific offset based on middle
        // - print nodes/ entries in that level with offset
        for(var sb: stringsAllHeights){
            //sb.setLength(sb.length() - 2);
            var halfStrLen = sb.length() >> 1;
            var offset = middle - halfStrLen;
            sb.insert(0, " ".repeat(offset));
            System.out.println(sb);
        }
    }


    private void print(int level, ArrayList<StringBuilder> sbs, int spaces){
        // One StringBuilder for each level (= height)
        // a new height and there is no StringBuilder yet (i.e. node is the first on this level)
        StringBuilder sb;
        if(level == sbs.size()){
            sb = new StringBuilder();
            sbs.add(sb);
        } else sb = sbs.get(level);

        // append own entries to string for this level
        sb.append(entries.toString()).append(" ".repeat(spaces));
        if(nextNodes.size() != 0)
            for(var child: nextNodes) child.print(level + 1, sbs, spaces);

    }

}
