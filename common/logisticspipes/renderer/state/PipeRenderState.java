package logisticspipes.renderer.state;

import java.io.IOException;

import logisticspipes.interfaces.IClientState;
import logisticspipes.network.LPDataInputStream;
import logisticspipes.network.LPDataOutputStream;
import logisticspipes.proxy.SimpleServiceLocator;
import logisticspipes.proxy.buildcraft.subproxies.IBCRenderState;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PipeRenderState implements IClientState {

	public final ConnectionMatrix pipeConnectionMatrix = new ConnectionMatrix();
	public final TextureMatrix textureMatrix = new TextureMatrix();
	public final IBCRenderState bcRenderState = SimpleServiceLocator.buildCraftProxy.getBCRenderState();
	
	/*
	 * This is a placeholder for the pipe renderer to set to a value that the BlockGenericPipe->TileGenericPipe will then return the the WorldRenderer
	 */
	@SideOnly(Side.CLIENT)
	public IIcon currentTexture;
	@SideOnly(Side.CLIENT)
	public IIcon[] textureArray;

	private boolean dirty = true;
	
	public void clean() {
		dirty = false;
		pipeConnectionMatrix.clean();
		textureMatrix.clean();
		bcRenderState.clean();
	}

	public boolean isDirty() {
		return dirty || pipeConnectionMatrix.isDirty() || textureMatrix.isDirty() || bcRenderState.isDirty();
	}

	public boolean needsRenderUpdate() {
		return pipeConnectionMatrix.isDirty() || textureMatrix.isDirty() || bcRenderState.needsRenderUpdate();
	}

	@Override
	public void writeData(LPDataOutputStream data) throws IOException {
		pipeConnectionMatrix.writeData(data);
		textureMatrix.writeData(data);
		bcRenderState.writeData(data);
	}

	@Override
	public void readData(LPDataInputStream data) throws IOException {
		pipeConnectionMatrix.readData(data);
		textureMatrix.readData(data);
		bcRenderState.readData(data);
	}
}