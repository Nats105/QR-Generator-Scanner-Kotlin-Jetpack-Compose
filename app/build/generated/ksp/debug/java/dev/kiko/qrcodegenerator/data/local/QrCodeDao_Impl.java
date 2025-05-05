package dev.kiko.qrcodegenerator.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import dev.kiko.qrcodegenerator.data.models.QrCodeEntity;
import dev.kiko.qrcodegenerator.data.models.QrCodeType;
import dev.kiko.qrcodegenerator.data.models.QrCodeTypeConverter;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QrCodeDao_Impl implements QrCodeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QrCodeEntity> __insertionAdapterOfQrCodeEntity;

  private final QrCodeTypeConverter __qrCodeTypeConverter = new QrCodeTypeConverter();

  private final EntityDeletionOrUpdateAdapter<QrCodeEntity> __deletionAdapterOfQrCodeEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteQrCodeById;

  public QrCodeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQrCodeEntity = new EntityInsertionAdapter<QrCodeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `qr_codes` (`id`,`type`,`content`,`imageBytes`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QrCodeEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __qrCodeTypeConverter.fromQrCodeType(entity.getType());
        statement.bindString(2, _tmp);
        statement.bindString(3, entity.getContent());
        if (entity.getImageBytes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindBlob(4, entity.getImageBytes());
        }
        statement.bindLong(5, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfQrCodeEntity = new EntityDeletionOrUpdateAdapter<QrCodeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `qr_codes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QrCodeEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteQrCodeById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM qr_codes WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertQrCode(final QrCodeEntity qrCode,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQrCodeEntity.insertAndReturnId(qrCode);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQrCode(final QrCodeEntity qrCode,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfQrCodeEntity.handle(qrCode);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQrCodeById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteQrCodeById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteQrCodeById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<QrCodeEntity>> getAllQrCodes() {
    final String _sql = "SELECT * FROM qr_codes ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"qr_codes"}, new Callable<List<QrCodeEntity>>() {
      @Override
      @NonNull
      public List<QrCodeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageBytes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<QrCodeEntity> _result = new ArrayList<QrCodeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QrCodeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final QrCodeType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __qrCodeTypeConverter.toQrCodeType(_tmp);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final byte[] _tmpImageBytes;
            if (_cursor.isNull(_cursorIndexOfImageBytes)) {
              _tmpImageBytes = null;
            } else {
              _tmpImageBytes = _cursor.getBlob(_cursorIndexOfImageBytes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new QrCodeEntity(_tmpId,_tmpType,_tmpContent,_tmpImageBytes,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
