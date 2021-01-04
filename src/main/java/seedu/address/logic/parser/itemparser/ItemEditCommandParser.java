package seedu.address.logic.parser.itemparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_METRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itemcommand.ItemEditCommand;
import seedu.address.logic.commands.itemcommand.ItemEditCommand.EditItemDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Tag;

/**
 * Parses input arguments and creates a new ItemEditCommand object
 */
public class ItemEditCommandParser implements Parser<ItemEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ItemEditCommand
     * and returns an ItemEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_SUPPLIER, PREFIX_TAG,
                        PREFIX_MAX_QUANTITY, PREFIX_METRIC);

        assert argMultimap != null;

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemEditCommand.MESSAGE_USAGE), pe);
        }

        EditItemDescriptor editItemDescriptor = new ItemEditCommand.EditItemDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editItemDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editItemDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_SUPPLIER).isPresent()) {
            editItemDescriptor.setSupplier(ParserUtil.parseSupplier(argMultimap.getValue(PREFIX_SUPPLIER).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editItemDescriptor::setTags);
        if (argMultimap.getValue(PREFIX_MAX_QUANTITY).isPresent()) {
            editItemDescriptor.setMaxQuantity(ParserUtil
                .parseMaxQuantity(argMultimap.getValue(PREFIX_MAX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_METRIC).isPresent()) {
            editItemDescriptor.setMetric(ParserUtil
                .parseMetric(argMultimap.getValue(PREFIX_METRIC).get()));
        }

        if (!editItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ItemEditCommand.MESSAGE_NOT_EDITED);
        }

        return new ItemEditCommand(index, editItemDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
