package com.cloud.cang.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * @author zhouhong
 * @version 1.0.0
 */
public class FileLoader {
	private URI uri;
	private InputStream stream;
	public FileLoader(String path) {
		Validate.notEmpty(path, "The path must be not empty");
		try {
			uri = createURI(path);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	public FileLoader(File file) {
		Validate.notNull(file, "The file must be not null");
		uri = file.toURI();
	}
	public FileLoader(URI uri) {
		Validate.notNull(uri, "The URI must be not null");
		this.uri = uri;
	}
	public FileLoader(InputStream stream, String fileName) {
		if (!(stream instanceof ZipInputStream || stream instanceof GZIPInputStream)) {
			try {
				if (fileName.toLowerCase().endsWith(".zip")) {
					this.stream = fromZipFile(stream);
				} else {
					this.stream = stream;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			this.stream = stream;
	}

	public String getInfo() {
		String info;
		if (uri != null) {
			info = uri.toString();
		} else {
			info = "Stream resource";
		}
		return info;
	}

	public boolean isReloadable() {

		boolean isStreamResettable = stream != null && stream.markSupported();

		return isStreamResettable || uri != null;
	}
	public static URI createURI(String path) throws URISyntaxException {
		URI createdURI = null;

		File f = new File(path);
		if (f.exists() && f.isFile() && f.canRead()) {
			createdURI = f.toURI();
		} else {

			StrBuilder workingPathBuilder = new StrBuilder(path);
			workingPathBuilder.replaceAll(" ", "%20");

			if (SystemUtils.IS_OS_WINDOWS && StringUtils.contains(path, "\\")) {

				workingPathBuilder.replaceAll("\\\\", "/");
			}

			if (!workingPathBuilder.contains(':')) {
				while (workingPathBuilder.startsWith("/")) {
					workingPathBuilder = workingPathBuilder.deleteCharAt(0);
				}
				workingPathBuilder.insert(0, "file:///");
			}

			createdURI = URI.create(workingPathBuilder.toString());
		}

		return createdURI;
	}

	protected InputStream openInputStream(URI uri) {
		InputStream ret = null;
		InputStream firstStream = null;
		try {
			// URI resourceURI = resourceURI(uri);
			if (uri.getScheme().equals("classpath")) {
				StrBuilder pathBuilder = new StrBuilder();
				pathBuilder.append(uri.toString());
				pathBuilder.replaceFirst("classpath:", "");
				// System.out.println(pathBuilder.toString());
				firstStream = getClass().getResourceAsStream(
						pathBuilder.toString());
			} else {
				URLConnection connection = uri.toURL().openConnection();
				firstStream = connection.getInputStream();
			}
			if (uri.toString().toLowerCase().endsWith(".zip")) {
				ret = fromZipFile(firstStream);
			} else {
				ret = firstStream;
			}
		} catch (IOException e) {

			throw new RuntimeException(e);
		}
		return ret;
	}

	private InputStream fromZipFile(InputStream inputStream) throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		zipInputStream.getNextEntry();
		return zipInputStream;
	}

	/*
	 * private URI resourceURI(final URI uri) throws MalformedURLException { if
	 * (uri.getScheme().equals("classpath")) { StrBuilder pathBuilder = new
	 * StrBuilder(); pathBuilder.append(uri.toString());
	 * pathBuilder.replaceFirst("classpath:", ""); return
	 * URI.create(getClass().getResource(pathBuilder.toString()) .toString()); }
	 * return uri; }
	 */

	private void releaseStream() {
		try {

			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		stream = null;
	}

	public void releaseAll() {
		if (stream != null) {
			releaseStream();
		}

		uri = null;
	}

	public InputStream getStream() {
		if (stream == null) {
			if (uri != null) {
				stream = openInputStream(uri);
			} else {
				throw new RuntimeException(
						"The resource can not be read, the stream is null");
			}
		}
		return stream;
	}

	public void resetStream() {
		if (stream.markSupported()) {
			try {
				stream.reset();
			} catch (IOException e) {
				releaseStream();
			}
		} else {
			releaseStream();
		}
	}
}
