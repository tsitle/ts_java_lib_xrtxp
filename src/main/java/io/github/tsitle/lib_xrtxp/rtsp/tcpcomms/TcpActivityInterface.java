package io.github.tsitle.lib_xrtxp.rtsp.tcpcomms;

import io.github.tsitle.lib_xrtxp.common.exceptions.TcpSocketActivityTimeoutException;

interface TcpActivityInterface {

	void checkTcpActivityTimeout() throws TcpSocketActivityTimeoutException;

	void resetTcpActivityTimeoutTimer();

}
