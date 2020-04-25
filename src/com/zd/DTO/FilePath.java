package com.zd.DTO;

public class FilePath {
	private String root;

	private String fileName;

	private Integer fileType;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "FilePath [root=" + root + ", fileName=" + fileName + ", fileType=" + fileType + "]";
	}

}
