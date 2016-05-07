
public class Patient {

	private int id;
	private int clumpThick;
	private int cellSize;
	private int cellShape;
	private int mAdhesion;
	private int epSize;
	private int bNucl;
	private int blChrom;
	private int nNucl;
	private int mitos;
	private int classed;

	public Patient(int id, int clumpThick, int cellSize, int cellShape, int mAdhesion, int epSize, int bNucl,
			int blChrom, int nNucl, int mitos, int classed) {
		this.id = id;
		this.clumpThick = clumpThick;
		this.cellSize = cellSize;
		this.cellShape = cellShape;
		this.mAdhesion = mAdhesion;
		this.epSize = epSize;
		this.bNucl = bNucl;
		this.blChrom = blChrom;
		this.nNucl = nNucl;
		this.mitos = mitos;
		this.classed = classed;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the clumpThick
	 */
	public int getClumpThick() {
		return clumpThick;
	}

	/**
	 * @return the cellSize
	 */
	public int getCellSize() {
		return cellSize;
	}

	/**
	 * @return the cellShape
	 */
	public int getCellShape() {
		return cellShape;
	}

	/**
	 * @return the mAdhesion
	 */
	public int getmAdhesion() {
		return mAdhesion;
	}

	/**
	 * @return the epSize
	 */
	public int getEpSize() {
		return epSize;
	}

	/**
	 * @return the bNucl
	 */
	public int getbNucl() {
		return bNucl;
	}

	/**
	 * @return the blChrom
	 */
	public int getBlChrom() {
		return blChrom;
	}

	/**
	 * @return the nNucl
	 */
	public int getnNucl() {
		return nNucl;
	}

	/**
	 * @return the mitos
	 */
	public int getMitos() {
		return mitos;
	}

	/**
	 * @return the classed
	 */
	public int getClassed() {
		return classed;
	}

}
