package github.chenupt.calendar.persistance;

import org.litepal.crud.DataSupport;

/**
 * Created by chenupt@gmail.com on 2015/1/3.
 * Description : Note entity
 */
public class Note extends DataSupport {

    private long id;
    private String content;
    private long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
