/**
 * @author qiweiwei
 *
 */
package com.sh3h.serverprovider.rpc.service;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.BuildConfig;
import com.sh3h.serverprovider.rpc.RPCHttpClient;

/**
 * AbstractApiService
 */
public abstract class BaseApiService {
	private static final String TAG = "BaseApiService";

	/**
	 * ApplicationContext对象
	 */
	private Context _context = null;

	private static String BASE_URL = "http://128.1.3.186:8094";
	// private static String BASE_URL = "http://128.1.3.186:8004";

	private final String ENCODING = "UTF-8";
	private final int CONNECTION_TIMEOUT = 30000;
	private final int EXECUTE_TIMEOUT = 30000;
	private static boolean IS_DEBUG = BuildConfig.DEBUG;

	private JSONRPCClient _rpcClient = null;

	/**
	 * 默认构造函数
	 */
	public BaseApiService() {
	}

	/**
	 * 构造函数
	 *
	 * @param context
	 */
	public BaseApiService(Context context) {
		this._context = context;
	}

	/**
	 * @return 返回服务基地址
	 */
	public String getBaseURL() {
		if (BASE_URL == null) {
//			 ConfigHelper systemSetting = ConfigHelper.loadSystemSetting();

			// String baseURL = context.getString(R.string.base_server_url);
			// BASE_URL =
			// systemSetting.getString(SystemSetting.PARAM_SERVER_BASE_URL);
		}

		return BASE_URL;
	}

	public static void setBaseURL(String url) {
		BASE_URL = url;
		LogUtil.i(TAG, "---setBaseURL---" + url);
	}

	public static void setDebug(boolean isDebug) {
		IS_DEBUG = isDebug;
	}

	/**
	 * @return 返回服务具体地址
	 */
	public abstract String getHandlerURL();

	/**
	 * 构建服务地址, 基地址通过系统配置得到
	 *
	 * @param handlerURL
	 *            具体Service的地址
	 */
	private String buildURL(String handlerURL) {
		return getBaseURL() + "/" + handlerURL;
	}

	/**
	 * @return 返回Context对象
	 */
	protected Context getContext() {
		return this._context;
	}

	/**
	 * @return 使用默认配置，获取RPCClient对象
	 */
	private JSONRPCClient getRPCClient() {
		return this.getRPCClient(this.buildURL(this.getHandlerURL()));
	}

	/**
	 * 获取RPCClient对象
	 *
	 * @return JSONRPCClient对象
	 */
	private JSONRPCClient getRPCClient(String serviceURL) {
		if (this._rpcClient == null) {
			// this._rpcClient = JSONRPCClient.create(this._serviceURL,
			// Versions.VERSION_1);
			this._rpcClient = new RPCHttpClient(serviceURL);

			// MainApplication app =
			// (MainApplication)this._context.getApplicationContext();
			//
			// ((RPCHttpClient)this._rpcClient).addExParams("X-JSON-RPC-DEVICE-ID",
			// app.getDeviceID());
			// ((RPCHttpClient)this._rpcClient).addExParams("X-JSON-RPC-SESSION-ID",
			// app.getSessionId());

			this._rpcClient.setEncoding(ENCODING);
			this._rpcClient.setConnectionTimeout(CONNECTION_TIMEOUT);
			this._rpcClient.setSoTimeout(EXECUTE_TIMEOUT);
			this._rpcClient.setDebug(IS_DEBUG);
		}

		return this._rpcClient;
	}

	/**
	 * 调用指定服务方法
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回Object对象
	 * @throws ApiException
	 */
	public Object call(String method, Object... params) throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().call(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回Object对象
	 * @throws ApiException
	 */
	public Object call(String method, JSONObject params) throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().call(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回String对象
	 * @throws ApiException
	 */
	public String callString(String method, Object... params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callString(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回String对象
	 * @throws ApiException
	 */
	public String callString(String method, JSONObject params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callString(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回integer值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回integer值
	 * @throws ApiException
	 */
	public int callInt(String method, Object... params) throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callInt(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回integer值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回integer值
	 * @throws ApiException
	 */
	public int callInt(String method, JSONObject params) throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return (Integer) this.getRPCClient().callInt(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回long值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回long值
	 * @throws ApiException
	 */
	public long callLong(String method, Object... params) throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callLong(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回long值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回long值
	 * @throws ApiException
	 */
	public long callLong(String method, JSONObject params) throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callLong(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回boolean值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回boolean值
	 * @throws ApiException
	 */
	public boolean callBoolean(String method, Object... params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callBoolean(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回boolean值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回boolean值
	 * @throws ApiException
	 */
	public boolean callBoolean(String method, JSONObject params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callBoolean(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回double值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回double值
	 * @throws ApiException
	 */
	public double callDouble(String method, Object... params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callDouble(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回double值
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回double值
	 * @throws ApiException
	 */
	public double callDouble(String method, JSONObject params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callDouble(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回JSON对象
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回JSONObject对象
	 * @throws ApiException
	 */
	public JSONObject callJSONObject(String method, Object... params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callJSONObject(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回JSON对象
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回JSONObject对象
	 * @throws ApiException
	 */
	public JSONObject callJSONObject(String method, JSONObject params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callJSONObject(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回JSON数组
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            可变参数，支持多个
	 * @return 返回JSONArray对象
	 * @throws ApiException
	 */
	public JSONArray callJSONArray(String method, Object... params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callJSONArray(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}

	/**
	 * 调用指定服务方法, 返回JSON数组
	 *
	 * @param method
	 *            方法名称
	 * @param params
	 *            JSONObject对象, 服务端复杂对象
	 * @return 返回JSONArray对象
	 * @throws ApiException
	 */
	public JSONArray callJSONArray(String method, JSONObject params)
			throws ApiException {
		if (!IS_DEBUG) {
			LogUtil.i(TAG, method);
		}

		try {
			return this.getRPCClient().callJSONArray(method, params);
		} catch (JSONRPCException e) {
			throw new ApiException("JSON PRC Http requst error,", e);
		}
	}
}
