package com.nicordesigns.site;

public class FileAttachment
{
    private String name;
    
    private String mimeContentType;


    private byte[] contents;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public byte[] getContents()
    {
        return contents;
    }

    public void setContents(byte[] contents)
    {
        this.contents = contents;
    }

	public String getMimeContentType() {
		return mimeContentType;
	}

	public void setMimeContentType(String mimeContentType) {
		this.mimeContentType = mimeContentType;
	}
}