public class Array
{
    public static void main(String args[])
    {
        int a[][]={{22,23,24},{25,26,27},{31,32,33}};
        for(int i=0; i< a.length;i++)
        {
            for(int j=0;j<a.length;j++)
            {
                System.out.print(a[i][j]+ " ");
            }
            System.out.println();
        }

    }
}
