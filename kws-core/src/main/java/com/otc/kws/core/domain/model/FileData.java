package com.otc.kws.core.domain.model;

import java.io.InputStream;

import lombok.Data;

@Data
public class FileData 
{
	protected byte[] bytes;
	protected long size;
	protected String contentType;
	protected String originalFileName;
	protected String fileExtension;
	protected InputStream inputStream;
}