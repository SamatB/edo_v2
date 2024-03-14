alter table agreement_list
    drop column if exists matching_block_coordinating_id,
    drop column if exists matching_block_signatory_id;

alter table matching_block
    drop column if exists participant_id,
    add column if not exists approval_block_type text not null default 'SIGNER_BLOCK';

alter table participant
    add column if not exists matching_block_id bigint references matching_block(id);