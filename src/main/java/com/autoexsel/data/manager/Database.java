package com.autoexsel.data.manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import java.sql.*;

import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Database {

	private final static char[] PASSWORD = "AshokenfldsgbnlsngdlksdsgmDikshitlorumepsumSapientqwertyuiiopConsulting"
			.toCharArray();
	private final static byte[] SALT = { (byte) 0x1024, (byte) 0x1028, (byte) 0x3039, (byte) 0xdfec, (byte) 0xfcde,
			(byte) 0x3853, (byte) 0x1109, (byte) 0x1768, };

	private static Statement statement = null;
	private static Connection conn = null;

	public Connection connectDatabase()
			throws SQLException, GeneralSecurityException, IOException, ClassNotFoundException {
		JSONObject jsonObj = (JSONObject) JSONLoader.getChildNodeFromJSON(JSONLoader.config, "env/qa/db");
		if (jsonObj.getString("url").startsWith("jdbc:postgresql://")) {
			if (conn == null) {
				conn = getPostgreSQLConnection();
			}
		} else if (jsonObj.getString("url").startsWith("jdbc:mysql://")) {
			if (conn == null) {
				conn = getMySQLConnection();
			}
		}
		return conn;
	}

	public Statement getStatement(Connection con) throws SQLException {
		if (statement == null) {
			statement = con.createStatement();
		}
		return statement;
	}

	public String fetchSingleValue(String queryString) {
		ResultSet rs = null;
		String result = "";
		if (conn == null) {
			Statement statement = null;
			try {
				connectDatabase();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				statement = getStatement(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = statement.executeQuery(queryString);
				if (rs.next()) {
					result = rs.getString(1);
				} else {
					result = "null";
				}
			} catch (SQLException e) {
				result = "null";
				e.printStackTrace();
			}
			closeDatabseConnection();
		} else {
			try {
				if (statement == null) {
					statement = getStatement(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = statement.executeQuery(queryString);
				if (rs.next()) {
					result = rs.getString(1);
				} else {
					result = "null";
				}
			} catch (SQLException e) {
				result = "null";
				e.printStackTrace();
			}
		}
		return result;
	}

	public ResultSet fetchAll(String queryString) {
		ResultSet rs = null;
		if (conn == null) {
			Statement statement = null;
			try {
				connectDatabase();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				statement = getStatement(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = statement.executeQuery(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeDatabseConnection();
		} else {
			try {
				if (statement == null) {
					statement = getStatement(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = statement.executeQuery(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	public void updateTable(String queryString) {
		if (conn == null) {
			Statement statement = null;
			try {
				connectDatabase();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				statement = getStatement(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.executeUpdate(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeDatabseConnection();
		} else {
			try {
				if (statement == null) {
					statement = getStatement(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.executeUpdate(queryString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeDatabseConnection() {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection getPostgreSQLConnection()
			throws SQLException, GeneralSecurityException, IOException, ClassNotFoundException {
		String url = JSONLoader.getConfigValue("env/qa/db/url");
		String username = JSONLoader.getConfigValue("env/qa/db/username");
		String password = JSONLoader.getConfigValue("env/qa/db/password");
		password = decrypt(password);
		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection(url, props);
		return conn;
	}

	private Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		String bdName = "com.mysql.jdbc.Driver";
		String url = "";
		String user = "";
		String password = "";
		Class.forName(bdName);
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public String getEncryptedPassword(String password) {
		SecretKeyFactory keyFactory = null;
		String encryptedValue = "";
		try {
			keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SecretKey key = null;
		try {
			key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		Cipher pbeCipher = null;
		try {
			pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		try {
			pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		try {
			encryptedValue = base64Encode(pbeCipher.doFinal(password.getBytes("UTF-8")));
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptedValue;
	}

	private String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	private String decrypt(String property) throws GeneralSecurityException, IOException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	}

	private byte[] base64Decode(String property) throws IOException {
		return new BASE64Decoder().decodeBuffer(property);
	}

}
