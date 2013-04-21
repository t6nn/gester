package eu.t6nn.gester.operations.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.t6nn.gester.Gester;
import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.exceptions.GesterException;
import eu.t6nn.gester.variables.Variable;

public class LocalDatabaseFeedbackStrategy extends AbstractFeedbackStrategy {

	private Logger LOG = LoggerFactory
			.getLogger(LocalDatabaseFeedbackStrategy.class);

	private Connection con;

	private boolean clearDatabase = false;

	private boolean prunedOnly = true;

	private int runId;

	private String dbName;

	public LocalDatabaseFeedbackStrategy(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public void beforeRun(Gester gester) {
		setupDatabase(dbName);
	}
	
	public LocalDatabaseFeedbackStrategy clearDatabase(boolean clear) {
		this.clearDatabase = clear;
		return this;
	}

	public LocalDatabaseFeedbackStrategy prunedOnly(boolean prunedOnly) {
		this.prunedOnly = prunedOnly;
		return this;
	}

	private void setupDatabase(String filename) {
		try {
			con = DriverManager.getConnection("jdbc:hsqldb:file:" + filename
					+ ";shutdown=true", "SA", "");

			if (clearDatabase) {
				execute("DROP TABLE IF EXISTS Variable;");
				execute("DROP TABLE IF EXISTS TestRun;");
			}

			execute("CREATE TABLE IF NOT EXISTS TestRun "
					+ "(ID INTEGER PRIMARY KEY, Start TIMESTAMP, End TIMESTAMP);");
			execute("CREATE TABLE IF NOT EXISTS Variable "
					+ "(Run INTEGER, Generation INTEGER, Rank INTEGER, "
					+ "Cost DOUBLE, Name VARCHAR(100), Value VARCHAR(1000));");
			execute("CREATE INDEX idx_cost ON Variable(Cost)");
			execute("CREATE INDEX idx_name ON Variable(Name)");
			execute("CREATE INDEX idx_value ON Variable(Value)");
			execute("CREATE INDEX idx_gen ON Variable(Generation)");
			execute("CREATE INDEX idx_rank ON Variable(Rank)");
		} catch (SQLException e) {
			throw new GesterException("Problem setting up the database (connection).", e);
		}

	}

	private void execute(String sql) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.execute();
		} finally {
			if (ps != null)
				ps.close();
		}
	}

	private void recordPopulation(Population pop, int generation) {
		try {
			PreparedStatement addIdentity = con.prepareStatement("INSERT INTO "
					+ "Variable (Run, Generation, Rank, Cost, Name, Value) "
					+ "VALUES (?, ?, ?, ?, ?, ?);");

			for (int i = 0; i < pop.size(); ++i) {
				Identity id = pop.get(i);
				Map<String, Variable> values = id.decode();
				for (Map.Entry<String, Variable> var : values.entrySet()) {
					addIdentity.setInt(1, runId);
					addIdentity.setInt(2, generation);
					addIdentity.setInt(3, i + 1);
					addIdentity.setDouble(4, id.lastCost());
					addIdentity.setString(5, var.getKey());
					addIdentity.setString(6, var.getValue().toString());
					addIdentity.addBatch();
				}
			}
			addIdentity.executeBatch();
			addIdentity.close();
		} catch (SQLException e) {
			throw new GesterException(e);
		}
	}

	@Override
	public void afterInitialization(Population pop) {
		try {
			ResultSet res = con.prepareStatement(
					"SELECT MAX(ID) + 1 FROM TestRun;").executeQuery();
			res.next();
			runId = res.getInt(1);
			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO TestRun (ID, Start) VALUES (?, CURRENT_TIMESTAMP)");
			stmt.setInt(1, runId);
			stmt.execute();
		} catch (SQLException e) {
			closeConnection();
			throw new GesterException(e);
		}

	}

	@Override
	public void afterUpdate(Population pop, int generation) {
		if (!prunedOnly) {
			recordPopulation(pop, generation);
		}
	}

	@Override
	public void afterPrune(Population pop, int generation) {
		if (prunedOnly) {
			recordPopulation(pop, generation);
		}
	}

	@Override
	public void afterConvergence(Population pop, int generation) {
		try {
			PreparedStatement stmt = con
					.prepareStatement("UPDATE TestRun SET End = CURRENT_TIMESTAMP WHERE ID = ?");
			stmt.setInt(1, runId);
			stmt.execute();
			
			if(prunedOnly) {
				recordPopulation(pop, generation);
			}
		} catch (SQLException e) {
			throw new GesterException(e);
		} finally {
			closeConnection();
		}
	}

	private void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				LOG.info("Unable to close connection.", e);
			}
		}
	}

}
