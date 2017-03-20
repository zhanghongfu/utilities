package dolf.zhang.utilities.page;

/**
 * @author dolf
 * @description
 * @date 16/9/29
 */
public class PageUtilities {

    private int total = 0;
    private int limit = 50;
    private int pages = 1;
    private int pageNumber = 1;
    //开始位置
    private int start = 0 ;
    //结束位置
    private int end  = 0 ;
    private boolean isFirstPage=false;
    private boolean isLastPage=false;
    private boolean hasPreviousPage=false;
    private boolean hasNextPage=false;


    public PageUtilities(int total, int pageNumber) {
        init(total, pageNumber, limit);
    }


    public PageUtilities(int total, int pageNumber, int limit) {
        init(total, pageNumber, limit);
    }

    public static void main(String[] args) {
        PageUtilities p = new PageUtilities(51 , 2 , 50);
        System.out.println(p);
        System.out.println(p.hasNextPage());
    }

    private void init(int total, int pageNumber, int limit){
        //设置基本参数
        this.total=total;
        this.limit=limit;
        this.pages=(this.total-1)/this.limit+1;

        //根据输入可能错误的当前号码进行自动纠正
        if(pageNumber<1){
            this.pageNumber=1;
        }else if(pageNumber>this.pages){
            this.pageNumber=this.pages;
        }else{
            this.pageNumber=pageNumber;
        }

        this.start = (this.pageNumber -1 )* limit ;
        this.end = (this.pageNumber  )* limit ;

        if(this.end  >  this.total) this.end = this.total;
        if(this.start < 1 ) this.start = 0 ;
        if(this.end < 1 ) this.end = limit ;

        //以及页面边界的判定
        border();
    }


    /**
     * 判定页面边界
     */
    private void border(){
        isFirstPage = pageNumber == 1;
        isLastPage = pageNumber == pages && pageNumber!=1;
        hasPreviousPage = pageNumber!=1;
        hasNextPage = pageNumber!=pages;
    }



    /**
     * 得到记录总数
     * @return {int}
     */
    public int getTotal() {
        return total;
    }

    /**
     * 得到每页显示多少条记录
     * @return {int}
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 得到页面总数
     * @return {int}
     */
    public int getPages() {
        return pages;
    }

    /**
     * 得到当前页号
     * @return {int}
     */
    public int getPageNumber() {
        return pageNumber;
    }



    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "PageUtilities{" +
                "total=" + total +
                ", limit=" + limit +
                ", pages=" + pages +
                ", pageNumber=" + pageNumber +
                ", start=" + start +
                ", end=" + end +
                ", isFirstPage=" + isFirstPage +
                ", isLastPage=" + isLastPage +
                ", hasPreviousPage=" + hasPreviousPage +
                ", hasNextPage=" + hasNextPage +
                '}';
    }
}
