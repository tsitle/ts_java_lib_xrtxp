package io.github.tsitle.lib_xrtxp.rtsp.interfaces;

import org.jspecify.annotations.NonNull;
import io.github.tsitle.lib_xrtxp.rtsp.exceptions.RtspProtoRtspParamInvalidValueException;
import io.github.tsitle.lib_xrtxp.rtsp.ids.RtspProtoIdInputSource;
import io.github.tsitle.lib_xrtxp.rtsp.ids.RtspProtoIdSession;
import io.github.tsitle.lib_xrtxp.rtsp.exceptions.RtspProtoRtspParamUnknownException;
import io.github.tsitle.lib_xrtxp.rtsp.ids.RtspProtoIdSubStream;

/**
 * Interface for setting RTSP parameters.
 */
public interface RtspProtoParameterSetterInterface {

	/**
	 * Set a parameter.
	 * @param dryRunOnly If true, do not actually set the parameter
	 * @param idSession Session ID (required)
	 * @param idInputSource Input Source ID (required)
	 * @param idSubStream Sub-Stream ID (can be empty)
	 * @param contentLanguage Content language (can be empty)
	 * @param key The parameter key
	 * @param value The parameter's value
	 * @throws RtspProtoRtspParamUnknownException If the parameter is unknown
	 * @throws RtspProtoRtspParamInvalidValueException If the parameter's value is invalid
	 */
	void setRtspParameter(
				boolean dryRunOnly,
				@NonNull RtspProtoIdSession idSession,
				@NonNull RtspProtoIdInputSource idInputSource,
				@NonNull RtspProtoIdSubStream idSubStream,
				@NonNull String contentLanguage,
				@NonNull String key,
				@NonNull String value
			) throws RtspProtoRtspParamUnknownException, RtspProtoRtspParamInvalidValueException;

}
