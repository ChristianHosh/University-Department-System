package defpack.project3ds;

public class TreeNode {
    Department data;
    TreeNode left;
    TreeNode right;
    int height;

    TreeNode(Department data){
        this.data = data;
    }

    public String getKey(){
        return data.getName();
    }

    public void setKey(String name){
        this.data.setName(name);
    }

    public String toString(){
        return data.toString();
    }
}
